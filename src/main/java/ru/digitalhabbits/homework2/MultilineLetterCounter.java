package ru.digitalhabbits.homework2;

import ru.digitalhabbits.homework2.impl.LetterCountMergerImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class MultilineLetterCounter implements TriFunction<String[], Integer, Integer, Map<Character, Long>> {

    private final ForkJoinPool countLettersPool;
    private final LetterCountMerger letterCountMerger;

    public MultilineLetterCounter() {
        this.countLettersPool = (ForkJoinPool) Executors.newWorkStealingPool();
        this.letterCountMerger = new LetterCountMergerImpl();
    }

    @Override
    public Map<Character, Long> apply(String[] lines, Integer beginInd, Integer endInd) {
        return Arrays.stream(lines)
                .skip(beginInd)
                .limit(endInd - beginInd + 1)
                .map(this::toCharacterArray)
                .map(this::toTask)
                .map(countLettersPool::invoke)
                .reduce(Collections.emptyMap(), letterCountMerger::merge);
    }

    private Character[] toCharacterArray(String line) {
        return line.chars()
                .mapToObj(c -> (char)c)
                .toArray(Character[]::new);
    }

    private CountLettersTask<Character> toTask(Character[] chars) {
        return new CountLettersTask.Builder<Character>()
                .array(chars)
                .beginInd(0)
                .endInd(chars.length - 1)
                .threshold(4)
                .mapper(new LetterCounter())
                .reducer(letterCountMerger)
                .build();
    }
}

package ru.digitalhabbits.homework2;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LetterCounter implements TriFunction<Character[], Integer, Integer, Map<Character, Long>> {

    @Override
    public Map<Character, Long> apply(Character[] line, Integer beginInd, Integer endInd) {
        return Arrays.stream(line)
                .skip(beginInd)
                .limit(endInd - beginInd + 1)
                .parallel()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}

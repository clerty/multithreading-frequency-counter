package ru.digitalhabbits.homework2.impl;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import ru.digitalhabbits.homework2.CountLettersTask;
import ru.digitalhabbits.homework2.FileLetterCounter;
import ru.digitalhabbits.homework2.FileReader;
import ru.digitalhabbits.homework2.MultilineLetterCounter;

public class AsyncFileLetterCounter implements FileLetterCounter {

    private final FileReader fileReader;
    private final ForkJoinPool countLettersPool;

    public AsyncFileLetterCounter() {
        this.fileReader = new FileReaderImpl();
        this.countLettersPool = (ForkJoinPool) Executors.newWorkStealingPool();
    }

    @Override
    public Map<Character, Long> count(File input) {
        final String[] lines = fileReader.readLines(input)
                .toArray(String[]::new);
        final CountLettersTask.Builder<String> taskBuilder = new CountLettersTask.Builder<String>()
                .array(lines)
                .beginInd(0)
                .endInd(lines.length - 1)
                .threshold(4)
                .mapper(new MultilineLetterCounter())
                .reducer(new LetterCountMergerImpl());
        return countLettersPool.invoke(taskBuilder.build());
    }
}

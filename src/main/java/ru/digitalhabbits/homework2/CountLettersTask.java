package ru.digitalhabbits.homework2;

import java.util.Map;
import java.util.concurrent.CountedCompleter;

public class CountLettersTask<T extends Comparable<T>> extends CountedCompleter<Map<Character, Long>> {

    private final T[] array;
    private final int beginInd;
    private final int endInd;
    private final int threshold;
    private final TriFunction<T[], Integer, Integer, Map<Character, Long>> mapper;
    private final LetterCountMerger reducer;
    private CountLettersTask<T> sibling;
    private Map<Character, Long> result;

    public CountLettersTask(Builder<T> builder) {
        super(builder.parent);
        this.array = builder.array;
        this.beginInd = builder.beginInd;
        this.endInd = builder.endInd;
        this.threshold = builder.threshold;
        this.mapper = builder.mapper;
        this.reducer = builder.reducer;
    }

    @Override
    public void compute() {
        if (this.endInd - this.beginInd >= this.threshold) {
            this.divideTask();
        } else {
            this.result = this.mapper.apply(this.array, this.beginInd, this.endInd);
            this.tryComplete();
        }
    }

    private void divideTask() {
        final int midInd = (this.beginInd + this.endInd) >>> 1;
        final Builder<T> builder = new Builder<>(this);
        final CountLettersTask<T> left = builder.endInd(midInd).build();
        final CountLettersTask<T> right = builder.beginInd(midInd + 1).endInd(this.endInd).build();
        left.sibling = right;
        right.sibling = left;

        this.setPendingCount(1);
        right.fork();
        left.compute();
    }

    @Override
    public void onCompletion(CountedCompleter<?> caller) {
        if (caller != this) {
            final CountLettersTask<T> child = (CountLettersTask<T>) caller;
            final CountLettersTask<T> childSib = child.sibling;
            this.result = childSib == null ? child.result : this.reducer.merge(child.result, childSib.result);
        }
    }

    @Override
    public Map<Character, Long> getRawResult() {
        return this.result;
    }

    public static class Builder<R extends Comparable<R>> {
        private CountLettersTask<R> parent;
        private R[] array;
        private int beginInd;
        private int endInd;
        private int threshold;
        private TriFunction<R[], Integer, Integer, Map<Character, Long>> mapper;
        private LetterCountMerger reducer;

        public Builder() {}

        public Builder(CountLettersTask<R> task) {
            this.parent = task;
            this.array = task.array;
            this.beginInd = task.beginInd;
            this.endInd = task.endInd;
            this.threshold = task.threshold;
            this.mapper = task.mapper;
            this.reducer = task.reducer;
        }

        public Builder<R> parent(CountLettersTask<R> parent) {
            this.parent = parent;
            return this;
        }

        public Builder<R> array(R[] array) {
            this.array = array;
            return this;
        }

        public Builder<R> beginInd(int beginInd) {
            this.beginInd = beginInd;
            return this;
        }

        public Builder<R> endInd(int endInd) {
            this.endInd = endInd;
            return this;
        }

        public Builder<R> threshold(int threshold) {
            this.threshold = threshold;
            return this;
        }

        public Builder<R> mapper(TriFunction<R[], Integer, Integer, Map<Character, Long>> mapper) {
            this.mapper = mapper;
            return this;
        }

        public Builder<R> reducer(LetterCountMerger reducer) {
            this.reducer = reducer;
            return this;
        }

        public CountLettersTask<R> build() {
            return new CountLettersTask<>(this);
        }
    }
}

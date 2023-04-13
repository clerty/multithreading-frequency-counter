package ru.digitalhabbits.homework2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.digitalhabbits.homework2.impl.LetterCountMergerImpl;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class LetterCountMergerTest {

    LetterCountMerger merger;

    @BeforeEach
    void setUp() {
        merger = new LetterCountMergerImpl();
    }

    @Test
    void merge_different_allEntries() {
        var first = Map.of(
                'a', 1L,
                'b', 2L,
                'c', 3L);
        var second = Map.of(
                'd', 3L,
                'e', 2L,
                'f', 1L);
        assertThat(merger.merge(first, second)).containsOnly(
                entry('a', 1L),
                entry('b', 2L),
                entry('c', 3L),
                entry('d', 3L),
                entry('e', 2L),
                entry('f', 1L)
        );
    }

    @Test
    void merge_intersected_sumOnSameKey() {
        var first = Map.of(
                'a', 1L,
                'b', 2L);
        var second = Map.of(
                'b', 2L,
                'c', 3L);
        assertThat(merger.merge(first, second)).containsOnly(
                entry('a', 1L),
                entry('b', 4L),
                entry('c', 3L)
        );
    }

    @Test
    void merge_bothEmpty_empty() {
        assertThat(merger.merge(Map.of(), Map.of())).isEmpty();
    }

    @Test
    void merge_firstEmpty_allEntriesFromFirst() {
        var first = Map.of(
                'a', 1L,
                'b', 2L,
                'c', 3L);
        assertThat(merger.merge(first, Map.of())).containsOnly(
                entry('a', 1L),
                entry('b', 2L),
                entry('c', 3L)
        );
    }

    @Test
    void merge_secondEmpty_allEntriesFromSecond() {
        var second = Map.of(
                'd', 3L,
                'e', 2L,
                'f', 1L);
        assertThat(merger.merge(Map.of(), second)).containsOnly(
                entry('d', 3L),
                entry('e', 2L),
                entry('f', 1L)
        );
    }
}

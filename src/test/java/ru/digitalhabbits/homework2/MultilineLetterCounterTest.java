package ru.digitalhabbits.homework2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class MultilineLetterCounterTest {

    MultilineLetterCounter counter;

    @BeforeEach
    void setUp() {
        counter = new MultilineLetterCounter();
    }

    @Test
    void apply_nonEmptyArray_ok() {
        var lines = new String[] {
                "fffcaacffffdedcf",
                "bbfedadafbabbcbb",
                "dfdaabfadaaaceac",
                "aafcddbbaecdbbbc"
        };
        assertThat(counter.apply(lines, 0, lines.length - 1)).containsOnly(
                entry('a', 15L),
                entry('b', 13L),
                entry('c', 9L),
                entry('d', 10L),
                entry('e', 4L),
                entry('f', 13L)
        );
    }

    @Test
    void apply_emptyArray_emptyMap() {
        assertThat(counter.apply(new String[]{}, 0, 0)).isEmpty();
    }
}

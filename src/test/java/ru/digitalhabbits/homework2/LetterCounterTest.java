package ru.digitalhabbits.homework2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class LetterCounterTest {

    private LetterCounter counter;

    @BeforeEach
    void setUp() {
        counter = new LetterCounter();
    }

    @Test
    void apply_nonEmptyString_ok() {
        var chars = "ddfbcacadffefaec".chars()
                .mapToObj(c -> (char)c)
                .toArray(Character[]::new);
        assertThat(counter.apply(chars, 0, chars.length - 1)).containsOnly(
                entry('a', 3L),
                entry('b', 1L),
                entry('c', 3L),
                entry('d', 3L),
                entry('e', 2L),
                entry('f', 4L)
        );
    }

    @Test
    void apply_emptyString_emptyMap() {
        assertThat(counter.apply(new Character[]{}, 0, 0)).isEmpty();
    }
}

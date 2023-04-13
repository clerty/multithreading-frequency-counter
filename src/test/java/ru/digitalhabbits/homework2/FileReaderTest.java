package ru.digitalhabbits.homework2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.digitalhabbits.homework2.impl.FileReaderImpl;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import static com.google.common.io.Resources.getResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileReaderTest {

    private FileReader reader;

    @BeforeEach
    void setUp() {
        reader = new FileReaderImpl();
    }

    @Test
    void readLines_existingFile_ok() {
        var file = new File(getResource("test.txt").getPath());
        var lines = reader.readLines(file);
        assertEquals(1000, lines.count());
    }

    @Test
    void readLines_nonExistingFile_exceptionThrown() {
         assertEquals(0, reader.readLines(new File("notExist.txt")).count());
    }
}

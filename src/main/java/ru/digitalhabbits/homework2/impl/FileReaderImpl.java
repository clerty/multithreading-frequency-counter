package ru.digitalhabbits.homework2.impl;

import org.slf4j.Logger;
import ru.digitalhabbits.homework2.FileReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

public class FileReaderImpl implements FileReader {

    private static final Logger logger = getLogger(FileReaderImpl.class);

    @Override
    public Stream<String> readLines(File file) {
        Stream<String> lines = Stream.empty();
        try {
            lines = Files.lines(file.toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return lines;
    }
}

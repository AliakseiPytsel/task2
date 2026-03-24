package com.textprocessor.reader.impl;

import com.textprocessor.exception.TextProcessingException;
import com.textprocessor.reader.TextReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextReaderImpl implements TextReader {

    private static final Logger logger = LogManager.getLogger(TextReaderImpl.class);

    @Override
    public String read(String resourceName) throws TextProcessingException {
        logger.info("Reading file: {}", resourceName);
        URL url = TextReaderImpl.class.getClassLoader().getResource(resourceName);
        if (url == null) {
            throw new TextProcessingException("Resource not found: " + resourceName);
        }
        try {
            String content = Files.readString(Path.of(url.toURI()), StandardCharsets.UTF_8);
            logger.info("File read successfully ({} chars)", content.length());
            return content;
        } catch (IOException | URISyntaxException e) {
            throw new TextProcessingException("Failed to read file: " + resourceName, e);
        }
    }
}
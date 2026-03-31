package com.textprocessor.reader;

import com.textprocessor.exception.TextParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResourceTextReader {

    private static final Logger logger = LogManager.getLogger(ResourceTextReader.class);

    public String readAll(String resourceName) throws TextParseException {
        URL url = ResourceTextReader.class.getClassLoader().getResource(resourceName);
        if (url == null) {
            throw new TextParseException("File not found: " + resourceName);
        }
        try {
            String content = Files.readString(Path.of(url.toURI()), StandardCharsets.UTF_8);
            logger.info("File read: {} chars", content.length());
            content = content.replaceAll("[ \\t]+", " ");
            return content.trim();
        } catch (IOException | URISyntaxException e) {
            throw new TextParseException(e);
        }
    }
}
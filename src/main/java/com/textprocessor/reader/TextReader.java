package com.textprocessor.reader;

import com.textprocessor.exception.TextProcessingException;

public interface TextReader {
    String read(String resourceName) throws TextProcessingException;
}
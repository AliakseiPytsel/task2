package com.textprocessor.parser;

import com.textprocessor.composite.TextComponent;
import com.textprocessor.exception.TextProcessingException;

public abstract class AbstractParser {
    private AbstractParser next;

    public AbstractParser setNext(AbstractParser next) {
        this.next = next;
        return next;
    }

    public AbstractParser getNext() {
        return next;
    }

    public abstract TextComponent parse(String text) throws TextProcessingException;
}
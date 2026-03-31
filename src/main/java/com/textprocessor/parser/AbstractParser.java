package com.textprocessor.parser;

import com.textprocessor.composite.TextComposite;

public abstract class AbstractParser {

    protected AbstractParser nextParser;

    public void setNextParser(AbstractParser nextParser) {
        this.nextParser = nextParser;
    }

    public abstract TextComposite parse(String text);
}
package com.textprocessor.parser;

import com.textprocessor.composite.TextComposite;
import com.textprocessor.composite.Letter;
import com.textprocessor.composite.Symbol;
import com.textprocessor.composite.DefaultTextComposite;
import com.textprocessor.type.ElementType;

public class WordParser extends AbstractParser {

    private static final String HYPHEN_OR_APOSTROPHE = "[-']";

    @Override
    public TextComposite parse(String text) {
        TextComposite word = new DefaultTextComposite(ElementType.WORD);
        for (char ch : text.toCharArray()) {
            if (String.valueOf(ch).matches(HYPHEN_OR_APOSTROPHE)) {
                word.add(new Symbol(ch));
            } else {
                word.add(new Letter(ch));
            }
        }
        return word;
    }
}
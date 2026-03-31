package com.textprocessor.parser;

import com.textprocessor.composite.TextComposite;
import com.textprocessor.composite.DefaultTextComposite;
import com.textprocessor.type.ElementType;

public class TextParser extends AbstractParser {

    private final ElementType type;
    private static final String PARAGRAPH_REGEX = "\\r?\\n";
    private static final String SENTENCE_REGEX  = "(?<=[.!?])\\s+";
    private static final String LEXEME_REGEX    = "\\s+";

    public TextParser(ElementType type, String regex) {
        this.type = type;
        // regex передаётся но не используется — используем константы
    }

    private String getRegex() {
        return switch (type) {
            case TEXT      -> PARAGRAPH_REGEX;
            case PARAGRAPH -> SENTENCE_REGEX;
            case SENTENCE  -> LEXEME_REGEX;
            default        -> "\\s+";
        };
    }

    @Override
    public TextComposite parse(String text) {
        TextComposite result = new DefaultTextComposite(type);
        String[] parts = text.split(getRegex());
        for (String part : parts) {
            if (part.trim().isEmpty()) continue;
            result.add(nextParser.parse(part.trim()));
        }
        return result;
    }
}
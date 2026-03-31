package com.textprocessor.parser;

import com.textprocessor.composite.TextComposite;
import com.textprocessor.composite.impl.Symbol;
import com.textprocessor.composite.impl.DefaultTextComposite;
import com.textprocessor.type.ElementType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexemeParser extends AbstractParser {

    private static final String WORD_OR_PUNCT_REGEX = "([\\p{L}\\p{N}-]+)|([.!?，,;:'\"«»()\\[\\]]+)";
    private static final String PUNCTUATION_REGEX   = "[.!?，,;:'\"«»()\\[\\]]+";

    @Override
    public TextComposite parse(String text) {
        TextComposite lexeme = new DefaultTextComposite(ElementType.LEXEME);
        Pattern pattern = Pattern.compile(WORD_OR_PUNCT_REGEX);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String token = matcher.group();
            if (token == null || token.isEmpty()) continue;
            if (token.matches(PUNCTUATION_REGEX)) {
                for (char ch : token.toCharArray()) {
                    lexeme.add(new Symbol(ch));
                }
            } else {
                lexeme.add(nextParser.parse(token));
            }
        }
        return lexeme;
    }
}
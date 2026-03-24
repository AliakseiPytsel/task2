package com.textprocessor.parser;

import com.textprocessor.composite.*;
import com.textprocessor.exception.TextProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordParser extends AbstractParser {

    private static final Logger logger = LogManager.getLogger(WordParser.class);
    private static final Pattern LETTER_PATTERN = Pattern.compile("\\p{L}+");
    private static final Pattern PUNCT_PATTERN  = Pattern.compile("[^\\p{L}]");

    @Override
    public TextComponent parse(String text) throws TextProcessingException {
        logger.debug("Parsing lexeme into words and punctuation: {}", text);
        Lexeme lexeme = new Lexeme();

        int i = 0;
        while (i < text.length()) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                StringBuilder wordChars = new StringBuilder();
                while (i < text.length() && Character.isLetter(text.charAt(i))) {
                    wordChars.append(text.charAt(i));
                    i++;
                }
                Word word = new Word();
                for (char letter : wordChars.toString().toCharArray()) {
                    word.add(new Symbol(letter, SymbolType.LETTER));
                }
                lexeme.add(word);
            } else {
                lexeme.add(new Symbol(c, SymbolType.PUNCTUATION));
                i++;
            }
        }
        return lexeme;
    }
}
package com.textprocessor.parser;

import com.textprocessor.composite.Sentence;
import com.textprocessor.composite.TextComponent;
import com.textprocessor.exception.TextProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class SentenceParser extends AbstractParser {

    private static final Logger logger = LogManager.getLogger(SentenceParser.class);
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");

    @Override
    public TextComponent parse(String text) throws TextProcessingException {
        logger.debug("Parsing sentence into lexemes");
        Sentence sentence = new Sentence();
        String[] tokens = WHITESPACE.split(text.strip());
        for (String token : tokens) {
            if (!token.isBlank()) {
                sentence.add(getNext().parse(token));
            }
        }
        return sentence;
    }
}
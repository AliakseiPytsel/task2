package com.textprocessor.parser;

import com.textprocessor.composite.Lexeme;
import com.textprocessor.composite.TextComponent;
import com.textprocessor.composite.TextLeaf;
import com.textprocessor.exception.TextProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LexemeParser extends AbstractParser {

    private static final Logger logger = LogManager.getLogger(LexemeParser.class);

    @Override
    public TextComponent parse(String text) throws TextProcessingException {
        logger.debug("Parsing lexeme: {}", text);
        Lexeme lexeme = new Lexeme();
        for (char c : text.toCharArray()) {
            lexeme.add(new TextLeaf(c));
        }
        return lexeme;
    }
}
package com.textprocessor.parser;

import com.textprocessor.composite.Paragraph;
import com.textprocessor.composite.TextComponent;
import com.textprocessor.exception.TextProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParser extends AbstractParser {

    private static final Logger logger = LogManager.getLogger(ParagraphParser.class);
    private static final Pattern SENTENCE_PATTERN = Pattern.compile("[^.!?]+[.!?]+");

    @Override
    public TextComponent parse(String text) throws TextProcessingException {
        logger.debug("Parsing paragraph into sentences");
        Paragraph paragraph = new Paragraph();
        Matcher matcher = SENTENCE_PATTERN.matcher(text);
        while (matcher.find()) {
            String sentenceText = matcher.group().strip();
            if (!sentenceText.isEmpty()) {
                paragraph.add(getNext().parse(sentenceText));
            }
        }
        if (paragraph.size() == 0) {
            paragraph.add(getNext().parse(text.strip()));
        }
        return paragraph;
    }
}
package com.textprocessor.parser;

import com.textprocessor.composite.TextComponent;
import com.textprocessor.composite.TextDocument;
import com.textprocessor.exception.TextProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class TextDocumentParser extends AbstractParser {

    private static final Logger logger = LogManager.getLogger(TextDocumentParser.class);
    private static final Pattern PARAGRAPH_DELIMITER = Pattern.compile("\\r?\\n");

    @Override
    public TextComponent parse(String text) throws TextProcessingException {
        logger.info("Parsing document into paragraphs");
        TextDocument document = new TextDocument();
        String[] paragraphs = PARAGRAPH_DELIMITER.split(text.trim());
        for (String paragraph : paragraphs) {
            String trimmed = paragraph.strip();
            if (!trimmed.isEmpty()) {
                document.add(getNext().parse(trimmed));
            }
        }
        logger.info("Document parsed: {} paragraph(s)", document.size());
        return document;
    }
}
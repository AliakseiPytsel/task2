package com.textprocessor;

import com.textprocessor.composite.TextComponent;
import com.textprocessor.composite.TextComposite;
import com.textprocessor.exception.TextParseException;
import com.textprocessor.parser.AbstractParser;
import com.textprocessor.parser.LexemeParser;
import com.textprocessor.parser.TextParser;
import com.textprocessor.parser.WordParser;
import com.textprocessor.reader.ResourceTextReader;
import com.textprocessor.service.DuplicateWordService;
import com.textprocessor.service.ComponentSortService;
import com.textprocessor.service.SwapService;
import com.textprocessor.type.ElementType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws TextParseException {
        ResourceTextReader reader = new ResourceTextReader();
        String rawText = reader.readAll("text.txt");

        AbstractParser wordParser   = new WordParser();
        AbstractParser lexemeParser = new LexemeParser();
        AbstractParser sentenceParser = new TextParser(ElementType.SENTENCE, "\\s+");
        AbstractParser paragraphParser = new TextParser(ElementType.PARAGRAPH, "(?<=[.!?])\\s+");
        AbstractParser textParser = new TextParser(ElementType.TEXT, "\\r?\\n");

        textParser.setNextParser(paragraphParser);
        paragraphParser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);
        lexemeParser.setNextParser(wordParser);

        TextComposite document = textParser.parse(rawText);

        System.out.println("=== RESTORED TEXT ===");
        System.out.println(document);
        System.out.printf("Total symbols: %d%n%n", document.getCount());

        System.out.println("=== TASK 1: Sentences with duplicate words ===");
        DuplicateWordService duplicateWordService = new DuplicateWordService();
        int count = duplicateWordService.countSentencesWithDuplicates(document);
        System.out.printf("Sentences with duplicate words: %d%n%n", count);

        System.out.println("=== TASK 2: Sentences sorted by lexeme count ===");
        ComponentSortService componentSortService = new ComponentSortService();
        List<TextComponent> sorted = componentSortService.sortBySentenceCount(document);
        sorted.forEach(s -> System.out.printf("[%2d lexemes] %s%n",
                ((TextComposite) s).getChildCount(), s));

        System.out.println("\n=== TASK 3: First <-> last lexeme swapped ===");
        SwapService swapService = new SwapService();
        TextComposite swapped = swapService.swapLexemes(document);
        System.out.println(swapped);

        logger.info("Done.");
    }
}
package com.textprocessor;

import com.textprocessor.composite.TextComponent;
import com.textprocessor.composite.TextComposite;
import com.textprocessor.parser.AbstractParser;
import com.textprocessor.parser.LexemeParser;
import com.textprocessor.parser.TextParser;
import com.textprocessor.parser.WordParser;
import com.textprocessor.service.DuplicateWordService;
import com.textprocessor.service.ComponentSortService;
import com.textprocessor.service.SwapService;
import com.textprocessor.type.ElementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextProcessorTest {

    private static final String SAMPLE =
            "The cat sat on the mat.\n" +
                    "The dog ran across the field.\n" +
                    "Stop.";

    private TextComposite document;
    private DuplicateWordService duplicateWordService;
    private ComponentSortService componentSortService;
    private SwapService swapService;

    @BeforeEach
    void setUp() {
        AbstractParser wordParser      = new WordParser();
        AbstractParser lexemeParser    = new LexemeParser();
        AbstractParser sentenceParser  = new TextParser(ElementType.SENTENCE,  "\\s+");
        AbstractParser paragraphParser = new TextParser(ElementType.PARAGRAPH, "(?<=[.!?])\\s+");
        AbstractParser textParser      = new TextParser(ElementType.TEXT,      "\\r?\\n");

        textParser.setNextParser(paragraphParser);
        paragraphParser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);
        lexemeParser.setNextParser(wordParser);

        document = textParser.parse(SAMPLE);

        duplicateWordService = new DuplicateWordService();
        componentSortService = new ComponentSortService();
        swapService     = new SwapService();
    }

    @Test
    void parsedDocumentHasCorrectParagraphCount() {
        assertEquals(3, document.getChildCount());
    }

    @Test
    void parsedParagraphHasCorrectSentenceCount() {
        AbstractParser wordParser      = new WordParser();
        AbstractParser lexemeParser    = new LexemeParser();
        AbstractParser sentenceParser  = new TextParser(ElementType.SENTENCE,  "\\s+");
        AbstractParser paragraphParser = new TextParser(ElementType.PARAGRAPH, "(?<=[.!?])\\s+");
        AbstractParser textParser      = new TextParser(ElementType.TEXT,      "\\r?\\n");
        textParser.setNextParser(paragraphParser);
        paragraphParser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);
        lexemeParser.setNextParser(wordParser);

        TextComposite doc = textParser.parse("The sky is blue. The grass is green.");
        TextComposite para = (TextComposite) doc.getChildren().get(0);
        assertEquals(2, para.getChildCount());
    }

    @Test
    void parsedSentenceHasCorrectLexemeCount() {
        TextComposite para     = (TextComposite) document.getChildren().get(0);
        TextComposite sentence = (TextComposite) para.getChildren().get(0);
        // "The cat sat on the mat." -> 6 лексем
        assertEquals(6, sentence.getChildCount());
    }

    @Test
    void countSymbolsIsPositive() {
        assertTrue(document.getCount() > 0);
    }

    @Test
    void restoreContainsOriginalWords() {
        String restored = document.toString();
        assertTrue(restored.contains("cat"));
        assertTrue(restored.contains("dog"));
        assertTrue(restored.contains("Stop"));
    }

    @Test
    void task1CountsCorrectly() {
        // "The cat sat on the mat." содержит дубликат "the"
        // "The dog ran across the field." содержит дубликат "the"
        int result = duplicateWordService.countSentencesWithDuplicates(document);
        assertTrue(result >= 2);
    }

    @Test
    void task1ZeroForNoDuplicates() {
        AbstractParser wordParser      = new WordParser();
        AbstractParser lexemeParser    = new LexemeParser();
        AbstractParser sentenceParser  = new TextParser(ElementType.SENTENCE,  "\\s+");
        AbstractParser paragraphParser = new TextParser(ElementType.PARAGRAPH, "(?<=[.!?])\\s+");
        AbstractParser textParser      = new TextParser(ElementType.TEXT,      "\\r?\\n");
        textParser.setNextParser(paragraphParser);
        paragraphParser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);
        lexemeParser.setNextParser(wordParser);

        TextComposite doc = textParser.parse("Birds fly high.");
        assertEquals(0, duplicateWordService.countSentencesWithDuplicates(doc));
    }

    @Test
    void task2SortedAscending() {
        List<TextComponent> sorted = componentSortService.sortBySentenceCount(document);
        for (int i = 0; i < sorted.size() - 1; i++) {
            assertTrue(
                    ((TextComposite) sorted.get(i)).getChildCount() <=
                            ((TextComposite) sorted.get(i + 1)).getChildCount()
            );
        }
    }

    @Test
    void task2ReturnsSameSentenceCount() {
        List<TextComponent> sorted = componentSortService.sortBySentenceCount(document);
        assertEquals(3, sorted.size());
    }

    @Test
    void task3SwapsFirstAndLast() {
        TextComposite para     = (TextComposite) document.getChildren().get(0);
        TextComposite sentence = (TextComposite) para.getChildren().get(0);
        String firstBefore = sentence.getChildren().get(0).toString();
        String lastBefore  = sentence.getChildren().get(sentence.getChildCount() - 1).toString();

        swapService.swapLexemes(document);

        String firstAfter = sentence.getChildren().get(0).toString();
        String lastAfter  = sentence.getChildren().get(sentence.getChildCount() - 1).toString();

        assertEquals(lastBefore,  firstAfter);
        assertEquals(firstBefore, lastAfter);
    }

    @Test
    void task3LexemeCountUnchanged() {
        TextComposite para     = (TextComposite) document.getChildren().get(0);
        TextComposite sentence = (TextComposite) para.getChildren().get(0);
        int before = sentence.getChildCount();
        swapService.swapLexemes(document);
        assertEquals(before, sentence.getChildCount());
    }
}
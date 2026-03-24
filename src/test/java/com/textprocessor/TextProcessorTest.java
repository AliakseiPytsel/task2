package com.textprocessor;

import com.textprocessor.composite.*;
import com.textprocessor.exception.TextProcessingException;
import com.textprocessor.parser.AbstractParser;
import com.textprocessor.parser.ParserChainFactory;
import com.textprocessor.service.TextService;
import com.textprocessor.service.impl.TextServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TextProcessorTest {

    private static final String SAMPLE =
            "The cat sat on the mat.\n" +
                    "The dog ran across the field.\n" +
                    "Stop.";

    private AbstractParser chain;
    private TextService service;

    @BeforeEach
    void setUp() {
        chain   = ParserChainFactory.getChain();
        service = new TextServiceImpl();
    }

    @Test
    void parsedDocumentHasCorrectParagraphCount() throws TextProcessingException {
        TextDocument doc = (TextDocument) chain.parse(SAMPLE);
        assertEquals(3, doc.size());
    }

    @Test
    void parsedParagraphHasCorrectSentenceCount() throws TextProcessingException {
        TextDocument doc = (TextDocument) chain.parse("The sky is blue. The grass is green.");
        AbstractTextComposite para = (AbstractTextComposite) doc.get(0);
        assertEquals(2, para.size());
    }

    @Test
    void parsedSentenceHasCorrectLexemeCount() throws TextProcessingException {
        TextDocument doc = (TextDocument) chain.parse("Birds fly over mountains.");
        AbstractTextComposite para     = (AbstractTextComposite) doc.get(0);
        AbstractTextComposite sentence = (AbstractTextComposite) para.get(0);
        assertEquals(4, sentence.size());
    }

    @Test
    void parsedLexemeHasCorrectSymbolCount() throws TextProcessingException {
        TextDocument doc = (TextDocument) chain.parse("River flows quickly.");
        AbstractTextComposite para     = (AbstractTextComposite) doc.get(0);
        AbstractTextComposite sentence = (AbstractTextComposite) para.get(0);
        AbstractTextComposite lexeme   = (AbstractTextComposite) sentence.get(0);
        AbstractTextComposite word     = (AbstractTextComposite) lexeme.get(0);
        assertEquals(5, word.size());
    }

    @Test
    void restoreContainsOriginalWords() throws TextProcessingException {
        TextDocument doc = (TextDocument) chain.parse("Autumn leaves fall slowly. Winter comes after.");
        String restored = doc.restore();
        assertTrue(restored.contains("Autumn"));
        assertTrue(restored.contains("leaves"));
        assertTrue(restored.contains("Winter"));
    }

    @Test
    void countSymbolsIsPositive() throws TextProcessingException {
        TextDocument doc = (TextDocument) chain.parse("Clouds move across the sky.");
        assertTrue(doc.countSymbols() > 0);
    }

    @Test
    void countSymbolsMatchesExpected() throws TextProcessingException {
        TextDocument doc = (TextDocument) chain.parse("Go.");
        AbstractTextComposite para     = (AbstractTextComposite) doc.get(0);
        AbstractTextComposite sentence = (AbstractTextComposite) para.get(0);
        assertEquals(3, sentence.countSymbols());
    }

    @Test
    void task1FindsSharedWord() throws TextProcessingException {
        TextDocument doc = (TextDocument) chain.parse(SAMPLE);
        Map<String, List<Sentence>> result = service.findMaxSentencesWithSameWord(doc);
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey("the"));
    }

    @Test
    void task1ReturnsMaxGroup() throws TextProcessingException {
        TextDocument doc = (TextDocument) chain.parse(SAMPLE);
        Map<String, List<Sentence>> result = service.findMaxSentencesWithSameWord(doc);
        int maxSize = result.values().stream().mapToInt(List::size).max().orElse(0);
        result.values().forEach(g -> assertEquals(maxSize, g.size()));
    }

    @Test
    void task1SingleSentenceReturnsEmpty() throws TextProcessingException {
        TextDocument doc = (TextDocument) chain.parse("The sun rises in the east.");
        assertTrue(service.findMaxSentencesWithSameWord(doc).isEmpty());
    }

    @Test
    void task2SortedAscending() throws TextProcessingException {
        TextDocument doc = (TextDocument) chain.parse(SAMPLE);
        List<Sentence> sorted = service.sortSentencesByLexemeCount(doc);
        for (int i = 0; i < sorted.size() - 1; i++) {
            assertTrue(sorted.get(i).size() <= sorted.get(i + 1).size());
        }
    }

    @Test
    void task2ReturnsSameSentenceCount() throws TextProcessingException {
        TextDocument doc = (TextDocument) chain.parse(SAMPLE);
        assertEquals(3, service.sortSentencesByLexemeCount(doc).size());
    }

    @Test
    void task3SwapsFirstAndLast() throws TextProcessingException {
        TextDocument doc = (TextDocument) chain.parse("The wind blows through ancient trees.");
        AbstractTextComposite para = (AbstractTextComposite) doc.get(0);
        Sentence sentence = (Sentence) para.get(0);
        String originalFirst = sentence.get(0).restore();
        String originalLast  = sentence.get(sentence.size() - 1).restore();
        service.swapFirstAndLastLexeme(doc);
        assertEquals(originalLast,  sentence.get(0).restore());
        assertEquals(originalFirst, sentence.get(sentence.size() - 1).restore());
    }

    @Test
    void task3SingleLexemeSentenceUnchanged() throws TextProcessingException {
        TextDocument doc = (TextDocument) chain.parse("Run.");
        AbstractTextComposite para = (AbstractTextComposite) doc.get(0);
        Sentence sentence = (Sentence) para.get(0);
        String before = sentence.restore();
        service.swapFirstAndLastLexeme(doc);
        assertEquals(before, sentence.restore());
    }

    @Test
    void task3LexemeCountUnchanged() throws TextProcessingException {
        TextDocument doc = (TextDocument) chain.parse(SAMPLE);
        AbstractTextComposite para = (AbstractTextComposite) doc.get(0);
        int before = ((Sentence) para.get(0)).size();
        service.swapFirstAndLastLexeme(doc);
        assertEquals(before, ((Sentence) para.get(0)).size());
    }
}
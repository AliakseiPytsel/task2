package com.textprocessor;

import com.textprocessor.composite.Sentence;
import com.textprocessor.composite.TextDocument;
import com.textprocessor.exception.TextProcessingException;
import com.textprocessor.parser.AbstractParser;
import com.textprocessor.parser.ParserChainFactory;
import com.textprocessor.reader.TextReader;
import com.textprocessor.reader.impl.TextReaderImpl;
import com.textprocessor.service.TextService;
import com.textprocessor.service.impl.TextServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws TextProcessingException {
        TextReader reader = new TextReaderImpl();
        String rawText = reader.read("text.txt");

        AbstractParser chain = ParserChainFactory.getChain();
        TextDocument document = (TextDocument) chain.parse(rawText);

        System.out.println("=== RESTORED TEXT ===");
        System.out.println(document.restore());
        System.out.printf("%nTotal symbols: %d%n%n", document.countSymbols());

        TextService service = new TextServiceImpl();

        System.out.println("=== TASK 1: Sentences with the same word ===");
        Map<String, List<Sentence>> task1 = service.findMaxSentencesWithSameWord(document);
        task1.forEach((word, sentences) -> {
            System.out.printf("Word \"%s\" in %d sentences:%n", word, sentences.size());
            sentences.forEach(s -> System.out.println("  -> " + s.restore()));
        });

        System.out.println("\n=== TASK 2: Sentences sorted by lexeme count ===");
        List<Sentence> sorted = service.sortSentencesByLexemeCount(document);
        sorted.forEach(s -> System.out.printf("[%2d lexemes] %s%n", s.size(), s.restore()));

        System.out.println("\n=== TASK 3: First <-> last lexeme swapped ===");
        service.swapFirstAndLastLexeme(document);
        System.out.println(document.restore());

        logger.info("Done.");
    }
}
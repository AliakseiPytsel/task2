package com.textprocessor.service;

import com.textprocessor.comparator.TextComparator;
import com.textprocessor.composite.TextComponent;
import com.textprocessor.composite.TextComposite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ComponentSortService {

    private static final Logger logger = LogManager.getLogger(ComponentSortService.class);

    public List<TextComponent> sortBySentenceCount(TextComposite composite) {
        logger.info("Task 2: sorting sentences by lexeme count");
        List<TextComponent> sentences = collectSentences(composite);
        sentences.sort(TextComparator.BY_LEXEME_COUNT);
        logger.info("Task 2 complete: {} sentences sorted", sentences.size());
        return sentences;
    }

    private List<TextComponent> collectSentences(TextComposite composite) {
        List<TextComponent> sentences = new ArrayList<>();
        for (TextComponent paragraph : composite.getChildren()) {
            sentences.addAll(((TextComposite) paragraph).getChildren());
        }
        return sentences;
    }
}
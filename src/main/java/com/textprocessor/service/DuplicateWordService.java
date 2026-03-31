package com.textprocessor.service;

import com.textprocessor.composite.TextComponent;
import com.textprocessor.composite.TextComposite;
import com.textprocessor.type.ElementType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class DuplicateWordService {

    private static final Logger logger = LogManager.getLogger(DuplicateWordService.class);

    public int countSentencesWithDuplicates(TextComposite text) {
        logger.info("Task 1: counting sentences with duplicate words");
        int count = 0;
        for (TextComponent paragraph : text.getChildren()) {
            for (TextComponent sentence : ((TextComposite) paragraph).getChildren()) {
                if (hasDuplicates(sentence)) {
                    count++;
                }
            }
        }
        logger.info("Task 1 complete: {} sentences with duplicates", count);
        return count;
    }

    private boolean hasDuplicates(TextComponent sentence) {
        Set<String> words = new HashSet<>();
        for (TextComponent lexeme : ((TextComposite) sentence).getChildren()) {
            for (TextComponent word : ((TextComposite) lexeme).getChildren()) {
                if (word.getType() == ElementType.WORD) {
                    String wordText = word.toString().toLowerCase();
                    if (!words.add(wordText)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
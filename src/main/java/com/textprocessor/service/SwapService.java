package com.textprocessor.service;

import com.textprocessor.composite.TextComponent;
import com.textprocessor.composite.TextComposite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class SwapService {

    private static final Logger logger = LogManager.getLogger(SwapService.class);

    public TextComposite swapLexemes(TextComposite textComposite) {
        logger.info("Task 3: swapping first and last lexemes");
        for (TextComponent paragraph : textComposite.getChildren()) {
            for (TextComponent sentence : ((TextComposite) paragraph).getChildren()) {
                List<TextComponent> lexemes = ((TextComposite) sentence).getChildren();
                Collections.swap(lexemes, 0, lexemes.size() - 1);
                ((TextComposite) sentence).setChildren(lexemes);
            }
        }
        logger.info("Task 3 complete");
        return textComposite;
    }
}
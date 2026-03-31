package com.textprocessor.comparator;

import com.textprocessor.composite.TextComponent;
import com.textprocessor.composite.TextComposite;

import java.util.Comparator;

public enum TextComparator implements Comparator<TextComponent> {
    BY_LEXEME_COUNT {
        @Override
        public int compare(TextComponent o1, TextComponent o2) {
            return Integer.compare(
                    ((TextComposite) o1).getChildCount(),
                    ((TextComposite) o2).getChildCount()
            );
        }
    }
}
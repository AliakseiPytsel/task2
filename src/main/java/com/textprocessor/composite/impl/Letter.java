package com.textprocessor.composite.impl;

import com.textprocessor.composite.TextComponent;
import com.textprocessor.type.ElementType;

public class Letter implements TextComponent {

    private final char letter;

    public Letter(char letter) {
        this.letter = letter;
    }

    @Override
    public ElementType getType() {
        return ElementType.LETTER;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public String getText() {
        return String.valueOf(letter);
    }

    @Override
    public String toString() {
        return String.valueOf(letter);
    }
}
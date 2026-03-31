package com.textprocessor.composite;

import com.textprocessor.type.ElementType;

public class Symbol implements TextComponent {

    private final char symbol;

    public Symbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public ElementType getType() {
        return ElementType.SYMBOL;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
package com.textprocessor.composite;

public class TextLeaf implements TextComponent {

    private final char symbol;

    public TextLeaf(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public String restore() {
        return String.valueOf(symbol);
    }

    @Override
    public int countSymbols() {
        return 1;
    }
}
package com.textprocessor.composite;

public class Symbol extends AbstractTextComposite {

    private final char character;
    private final SymbolType type;

    public Symbol(char character, SymbolType type) {
        this.character = character;
        this.type = type;
    }

    public SymbolType getType() {
        return type;
    }

    @Override
    public String restore() {
        return String.valueOf(character);
    }

    @Override
    public int countSymbols() {
        return 1;
    }
}
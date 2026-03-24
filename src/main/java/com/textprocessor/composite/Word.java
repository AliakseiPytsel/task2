package com.textprocessor.composite;

public class Word extends AbstractTextComposite {

    @Override
    public String restore() {
        StringBuilder sb = new StringBuilder();
        for (TextComponent child : getChildren()) {
            sb.append(child.restore());
        }
        return sb.toString();
    }
}
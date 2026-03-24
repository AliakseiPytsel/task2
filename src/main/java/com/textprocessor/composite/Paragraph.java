package com.textprocessor.composite;

import java.util.stream.Collectors;

public class Paragraph extends AbstractTextComposite {

    @Override
    public String restore() {
        return getChildren().stream()
                .map(TextComponent::restore)
                .collect(Collectors.joining(" "));
    }
}
package com.textprocessor.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractTextComposite implements TextComponent {

    private final List<TextComponent> children = new ArrayList<>();

    public void add(TextComponent component) {
        children.add(component);
    }

    public void set(int index, TextComponent component) {
        children.set(index, component);
    }

    public TextComponent get(int index) {
        return children.get(index);
    }

    public List<TextComponent> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public int size() {
        return children.size();
    }

    @Override
    public int countSymbols() {
        return children.stream().mapToInt(TextComponent::countSymbols).sum();
    }
}
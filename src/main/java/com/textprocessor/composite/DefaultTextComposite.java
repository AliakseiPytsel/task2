package com.textprocessor.composite;

import com.textprocessor.type.ElementType;

import java.util.ArrayList;
import java.util.List;

public class DefaultTextComposite implements TextComposite {

    private final ElementType type;
    private final List<TextComponent> components = new ArrayList<>();

    public DefaultTextComposite(ElementType type) {
        this.type = type;
    }

    @Override
    public ElementType getType() {
        return type;
    }

    @Override
    public void add(TextComponent component) {
        components.add(component);
    }

    @Override
    public int getChildCount() {
        return components.size();
    }

    @Override
    public List<TextComponent> getChildren() {
        return new ArrayList<>(components);
    }

    @Override
    public void setChildren(List<TextComponent> children) {
        components.clear();
        components.addAll(children);
    }

    @Override
    public int getCount() {
        int count = 0;
        for (TextComponent component : components) {
            count += component.getCount();
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TextComponent component : components) {
            sb.append(component.toString());
        }
        if (type == ElementType.LEXEME) {
            sb.append(" ");
        }
        if (type == ElementType.PARAGRAPH) {
            sb.append("\n");
        }
        return sb.toString();
    }
}
package com.textprocessor.composite;

import com.textprocessor.type.ElementType;

public interface TextComponent {
    ElementType getType();
    int getCount();
}
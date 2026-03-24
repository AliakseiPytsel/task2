package com.textprocessor.service;

import com.textprocessor.composite.Sentence;
import com.textprocessor.composite.TextDocument;

import java.util.List;
import java.util.Map;

public interface TextService {
    Map<String, List<Sentence>> findMaxSentencesWithSameWord(TextDocument document);
    List<Sentence> sortSentencesByLexemeCount(TextDocument document);
    void swapFirstAndLastLexeme(TextDocument document);
}
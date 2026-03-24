package com.textprocessor.service.impl;

import com.textprocessor.composite.*;
import com.textprocessor.service.TextService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class TextServiceImpl implements TextService {

    private static final Logger logger = LogManager.getLogger(TextServiceImpl.class);

    @Override
    public Map<String, List<Sentence>> findMaxSentencesWithSameWord(TextDocument document) {
        logger.info("Task 1: finding sentences with common words");
        List<Sentence> allSentences = collectSentences(document);

        Map<String, List<Sentence>> wordIndex = new LinkedHashMap<>();
        for (Sentence sentence : allSentences) {
            Set<String> words = extractWords(sentence);
            for (String word : words) {
                wordIndex.computeIfAbsent(word, k -> new ArrayList<>()).add(sentence);
            }
        }

        int maxCount = wordIndex.values().stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);

        logger.info("Task 1 complete: max = {} sentences", maxCount);

        return wordIndex.entrySet().stream()
                .filter(e -> e.getValue().size() == maxCount && maxCount > 1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new));
    }

    @Override
    public List<Sentence> sortSentencesByLexemeCount(TextDocument document) {
        logger.info("Task 2: sorting sentences by lexeme count");
        List<Sentence> sentences = collectSentences(document);
        sentences.sort(Comparator.comparingInt(AbstractTextComposite::size));
        logger.info("Task 2 complete: {} sentences sorted", sentences.size());
        return sentences;
    }

    @Override
    public void swapFirstAndLastLexeme(TextDocument document) {
        logger.info("Task 3: swapping first and last lexemes");
        for (Sentence sentence : collectSentences(document)) {
            int size = sentence.size();
            if (size < 2) continue;
            TextComponent first = sentence.get(0);
            TextComponent last  = sentence.get(size - 1);
            sentence.set(0, last);
            sentence.set(size - 1, first);
        }
        logger.info("Task 3 complete");
    }

    private List<Sentence> collectSentences(TextDocument document) {
        List<Sentence> result = new ArrayList<>();
        for (TextComponent paragraphComp : document.getChildren()) {
            AbstractTextComposite paragraph = (AbstractTextComposite) paragraphComp;
            for (TextComponent sentenceComp : paragraph.getChildren()) {
                result.add((Sentence) sentenceComp);
            }
        }
        return result;
    }

    private Set<String> extractWords(Sentence sentence) {
        Set<String> words = new HashSet<>();
        for (TextComponent lexemeComp : sentence.getChildren()) {
            String word = lexemeComp.restore().toLowerCase().replaceAll("[^\\p{L}]", "");
            if (!word.isBlank()) {
                words.add(word);
            }
        }
        return words;
    }
}
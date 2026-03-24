package com.textprocessor.parser;

public final class ParserChainFactory {

    private static final AbstractParser CHAIN;

    static {
        AbstractParser documentParser  = new TextDocumentParser();
        AbstractParser paragraphParser = new ParagraphParser();
        AbstractParser sentenceParser  = new SentenceParser();
        AbstractParser wordParser      = new WordParser();

        documentParser.setNext(paragraphParser)
                .setNext(sentenceParser)
                .setNext(wordParser);

        CHAIN = documentParser;
    }

    private ParserChainFactory() {}

    public static AbstractParser getChain() {
        return CHAIN;
    }
}
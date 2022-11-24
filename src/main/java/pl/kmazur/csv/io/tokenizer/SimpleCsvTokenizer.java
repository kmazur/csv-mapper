package pl.kmazur.csv.io.tokenizer;

import pl.kmazur.csv.io.InputSource;

public class SimpleCsvTokenizer implements CsvTokenizer {

    private static final int END_OF_CONTENT = -1;

    private final SeekableInputSource source;
    private final CsvFormatSettings settings;
    private CsvToken currentToken;
    private String currentValue;

    public SimpleCsvTokenizer(InputSource source, CsvFormatSettings settings) {
        this.source = new SeekableInputSource(source);
        this.settings = settings;
    }

    @Override
    public CsvToken getCurrentToken() {
        return currentToken;
    }

    @Override
    public CsvToken getNextToken() {
        int c = source.read();
        if (c == END_OF_CONTENT) {
            return CsvToken.EOF;
        }

        if (c == settings.getValueSeparator()) {
            currentToken = CsvToken.VALUE_SEPARATOR;
            currentValue = settings.getValueSeparatorAsString();
            return currentToken;
        } else if (c == settings.getQuote()) {
            currentToken = CsvToken.VALUE;
            currentValue = readQuotedValue();
            return currentToken;
        } else if (isRowSeparator(c)) {
            source.skip(settings.getRowSeparator().length() - 1);
            currentToken = CsvToken.ROW_SEPARATOR;
            currentValue = settings.getRowSeparator();
            return currentToken;
        } else {
            currentValue = readUnquotedString(c);
            currentToken = CsvToken.VALUE;
            return currentToken;
        }
    }

    private boolean isRowSeparator(int firstChar) {
        String rowSeparator = settings.getRowSeparator();
        if (rowSeparator.codePointAt(0) == firstChar) {
            for (int i = 1; i < rowSeparator.length(); ) {
                int c = rowSeparator.codePointAt(i);
                int inputChar = source.peekRead();
                if (inputChar != c) {
                    source.resetPeek();
                    return false;
                }

                i += Character.charCount(c);
            }
            return true;
        }
        return false;
    }

    private String readQuotedValue() {
        StringBuilder builder = new StringBuilder();
        builder.append(Character.toChars(settings.getQuote()));

        boolean escaping = false;
        while (true) {
            int c = source.read();
            if (c == END_OF_CONTENT) {
                break;
            }

            builder.append(Character.toChars(c));
            if (escaping) {
                escaping = false;
            } else if (c == settings.getEscape()) {
                escaping = true;
            } else if (c == settings.getQuote()) {
                break;
            }
        }

        if (escaping) {
            throw new IllegalCsvValueException();
        }
        return builder.toString();
    }

    private String readUnquotedString(int firstChar) {
        int valueSeparator = settings.getValueSeparator();
        String rowSeparator = settings.getRowSeparator();

        StringBuilder builder = new StringBuilder();
        builder.append(Character.toChars(firstChar));
        boolean escaping = false;
        while (true) {
            int c = source.peekRead();
            if (c == END_OF_CONTENT) {
                break;
            }

            if (escaping) {
                escaping = false;
                builder.append(Character.toChars(c));
                source.read();
            } else if (c == settings.getEscape()) {
                escaping = true;
                builder.append(Character.toChars(c));
                source.read();
            } else if (c == valueSeparator) {
                source.resetPeek();
                return builder.toString();
            } else if (c == rowSeparator.codePointAt(0)) {
                if (isRowSeparator(c)) {
                    source.resetPeek();
                    return builder.toString();
                } else {
                    source.read();
                    builder.append(Character.toChars(c));
                }
            } else {
                builder.append(Character.toChars(c));
                source.read();
            }
        }

        if (escaping) {
            throw new IllegalCsvValueException();
        }
        return builder.toString();
    }

    @Override
    public String getTokenValue() {
        return switch (currentToken) {
            case VALUE -> currentValue;
            case ROW_SEPARATOR -> settings.getRowSeparator();
            case VALUE_SEPARATOR -> settings.getValueSeparatorAsString();
            case EOF -> throw new CsvEofException();
        };
    }

}

package pl.kmazur.csv.io.tokenizer;

public class CsvFormatSettings {

    public static final char DEFAULT_ESCAPE_CHARACTER = '\\';
    public static final char DEFAULT_QUOTE_CHARACTER = '"';
    public static final char DEFAULT_VALUE_SEPARATOR_CHARACTER = ',';

    private final int escape;
    private final int quote;
    private final int valueSeparator;
    private final String valueSeparatorStr;
    private final String rowSeparator;

    public CsvFormatSettings() {
        this(DEFAULT_ESCAPE_CHARACTER, DEFAULT_QUOTE_CHARACTER, DEFAULT_VALUE_SEPARATOR_CHARACTER);
    }

    public CsvFormatSettings(int escape, int quote, int valueSeparator) {
        this(escape, quote, valueSeparator, System.lineSeparator());
    }

    public CsvFormatSettings(int escape, int quote, int valueSeparator, String rowSeparator) {
        validate(escape, quote, valueSeparator, rowSeparator);

        this.escape = escape;
        this.quote = quote;
        this.valueSeparator = valueSeparator;
        this.valueSeparatorStr = new String(Character.toChars(valueSeparator));
        this.rowSeparator = rowSeparator;
    }

    private void validate(int escape, int quote, int valueSeparator, String rowSeparator) {
        if (escape <= 0) {
            throw new IllegalArgumentException("Escape must be positive value");
        }
        if (quote <= 0) {
            throw new IllegalArgumentException("Quote must be positive value");
        }
        if (valueSeparator <= 0) {
            throw new IllegalArgumentException("Value separator must be positive value");
        }
        if (rowSeparator == null || rowSeparator.isEmpty()) {
            throw new IllegalArgumentException("Row separator must not be empty or null");
        }

        if (escape == quote || escape == valueSeparator || quote == valueSeparator) {
            throw new IllegalArgumentException("All values: escape, quote, valueSeparator must be different");
        }
        if (rowSeparator.codePointCount(0, rowSeparator.length()) == 1) {
            int rowSeparatorCodePoint = rowSeparator.codePointAt(0);
            if (rowSeparatorCodePoint == escape ||
                    rowSeparatorCodePoint == quote ||
                    rowSeparatorCodePoint == valueSeparator) {
                throw new IllegalArgumentException(
                        "rowSeparator must be different than escape, quote and valueSeparator");
            }
        }
    }

    public int getEscape() {
        return escape;
    }

    public int getQuote() {
        return quote;
    }

    public int getValueSeparator() {
        return valueSeparator;
    }

    public String getRowSeparator() {
        return rowSeparator;
    }

    public String getValueSeparatorAsString() {
        return valueSeparatorStr;
    }

}

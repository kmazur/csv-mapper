package pl.kmazur.csv.io.element;

import pl.kmazur.csv.io.tokenizer.CsvFormatSettings;

public class UnquoteElementConverter implements CsvElementConverter {

    private final CsvFormatSettings settings;

    public UnquoteElementConverter(CsvFormatSettings settings) {
        this.settings = settings;
    }

    @Override
    public String apply(String value) {
        if (value == null) {
            return null;
        }

        int quote = settings.getQuote();
        char[] chars = Character.toChars(quote);
        int quoteSize = chars.length;
        if (value.length() < 2 * quoteSize) {
            return value;
        }

        int first = value.codePointAt(0);
        int last = value.codePointAt(value.length() - 1);
        if (first == quote && last == quote) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

}

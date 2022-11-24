package pl.kmazur.csv.io.element;

import pl.kmazur.csv.io.tokenizer.CsvFormatSettings;

public class UnescapeElementConverter implements CsvElementConverter {

    private final CsvFormatSettings settings;

    public UnescapeElementConverter(CsvFormatSettings settings) {
        this.settings = settings;
    }

    @Override
    public String apply(String value) {
        if (value == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        int escapeCodePoint = settings.getEscape();
        boolean escaping = false;
        for (int i = 0; i < value.length(); ) {
            int c = value.codePointAt(i);

            if (escaping) {
                builder.append(interpretEscape(c));
                escaping = false;
            } else if (c == escapeCodePoint) {
                escaping = true;
            } else {
                builder.append(Character.toChars(c));
            }

            i += Character.charCount(c);
        }
        return builder.toString();
    }

    private static String interpretEscape(int c) {
        return switch (c) {
            case 't' -> "\t";
            case 'r' -> "\r";
            case 'f' -> "\f";
            case 'n' -> "\n";
            case '\\' -> "\\";
            case 'b' -> "\b";
            default -> String.valueOf(Character.toChars(c));
        };
    }

}

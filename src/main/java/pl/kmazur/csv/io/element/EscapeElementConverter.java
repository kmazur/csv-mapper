package pl.kmazur.csv.io.element;

public class EscapeElementConverter implements CsvElementConverter {

    private final int escapeCodePoint;

    public EscapeElementConverter(int escapeCodePoint) {
        this.escapeCodePoint = escapeCodePoint;
    }

    @Override
    public String apply(String value) {
        if (value == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        char[] escapeChars = Character.toChars(escapeCodePoint);
        for (int i = 0; i < value.length(); ) {
            int c = value.codePointAt(i);

            switch (c) {
                case '\t' -> builder.append(escapeChars).append("t");
                case '\r' -> builder.append(escapeChars).append("r");
                case '\f' -> builder.append(escapeChars).append("f");
                case '\n' -> builder.append(escapeChars).append("n");
                case '"' -> builder.append(escapeChars).append("\"");
                case '\b' -> builder.append(escapeChars).append("b");
                default -> {
                    if (c == escapeCodePoint) {
                        builder.append(escapeChars).append(Character.toChars(c));
                    } else {
                        builder.append(String.valueOf(Character.toChars(c)));
                    }
                }
            }

            i += Character.charCount(c);
        }
        return builder.toString();
    }

}

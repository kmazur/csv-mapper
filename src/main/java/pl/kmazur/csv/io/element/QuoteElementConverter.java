package pl.kmazur.csv.io.element;

public class QuoteElementConverter implements CsvElementConverter {

    private final String quoteStr;

    public QuoteElementConverter(int quoteCodePoint) {
        this.quoteStr = String.valueOf(Character.toChars(quoteCodePoint));
    }

    @Override
    public String apply(String value) {
        if (value == null) {
            return null;
        }
        return quoteStr + value + quoteStr;
    }

}

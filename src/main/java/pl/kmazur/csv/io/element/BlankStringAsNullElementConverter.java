package pl.kmazur.csv.io.element;

public class BlankStringAsNullElementConverter implements CsvElementConverter {

    @Override
    public String apply(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value;
    }

}

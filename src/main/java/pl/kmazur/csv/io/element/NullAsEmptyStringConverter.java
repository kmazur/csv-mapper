package pl.kmazur.csv.io.element;

public class NullAsEmptyStringConverter implements CsvElementConverter {

    @Override
    public String apply(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }

}

package pl.kmazur.csv.io.element;

public class TrimElementConverter implements CsvElementConverter {

    @Override
    public String apply(String value) {
        if (value == null) {
            return null;
        }
        return value.trim();
    }

}

package pl.kmazur.csv.serializer;

import pl.kmazur.csv.CsvException;

public class CsvSerializationException extends CsvException {

    public CsvSerializationException() {
    }

    public CsvSerializationException(String message) {
        super(message);
    }

    public CsvSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvSerializationException(Throwable cause) {
        super(cause);
    }
}

package pl.kmazur.csv.deserializer;

import pl.kmazur.csv.CsvException;

public class CsvDeserializationException extends CsvException {

    public CsvDeserializationException() {
    }

    public CsvDeserializationException(String message) {
        super(message);
    }

    public CsvDeserializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvDeserializationException(Throwable cause) {
        super(cause);
    }

}

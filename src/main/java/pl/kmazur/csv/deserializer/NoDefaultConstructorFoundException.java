package pl.kmazur.csv.deserializer;

public class NoDefaultConstructorFoundException extends CsvDeserializationException {
    public NoDefaultConstructorFoundException(NoSuchMethodException e) {
        super(e);
    }
}

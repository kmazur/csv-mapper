package pl.kmazur.csv.converter;

import pl.kmazur.csv.deserializer.CsvDeserializationException;

public class ConverterNotFoundException extends CsvDeserializationException {

    public ConverterNotFoundException(Class<?> declaringClass, String name, Class<?> type) {
        super(String.format("No converter found for %s.%s of type %s", declaringClass.getCanonicalName(), name, type.getCanonicalName()));
    }
}

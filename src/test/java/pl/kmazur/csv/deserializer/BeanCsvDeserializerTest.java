package pl.kmazur.csv.deserializer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.kmazur.csv.converter.registry.StandardConverterRegistry;

import java.util.List;

class BeanCsvDeserializerTest {

    static class NoDefaultConstructorCsvRecord {
        public NoDefaultConstructorCsvRecord(String test) {
        }
    }

    static class PrivateConstructorCsvRecord {
        private PrivateConstructorCsvRecord() {
        }
    }

    @Test
    void shouldFailOnNoDefaultConstructor() {
        // given
        var deserializer = new BeanCsvDeserializer(new StandardConverterRegistry(),
                new DefaultConstructorInstanceFactory());

        // then
        Assertions.assertThrows(NoDefaultConstructorFoundException.class, () ->
                deserializer.deserialize(List.of(), NoDefaultConstructorCsvRecord.class)
        );

    }

    @Test
    void shouldFailOnPrivateDefaultConstructor() {
        // given
        var deserializer = new BeanCsvDeserializer(new StandardConverterRegistry(),
                new DefaultConstructorInstanceFactory());

        // then
        Assertions.assertThrows(CsvDeserializationException.class, () ->
                deserializer.deserialize(List.of(), PrivateConstructorCsvRecord.class)
        );

    }

}
package pl.kmazur.csv;

import org.junit.jupiter.api.Test;
import pl.kmazur.csv.annotation.CsvIndexedField;
import pl.kmazur.csv.converter.registry.StandardConverterRegistry;
import pl.kmazur.csv.serializer.BeanCsvSerializer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BeanCsvSerializerTest {

    static class UnknownType {

    }

    static class UnknownTypeCsvRecord {
        @CsvIndexedField(0)
        UnknownType field;
    }

    @Test
    void shouldSerializeNullValue() {
        // given
        var serializer = new BeanCsvSerializer(new StandardConverterRegistry());

        // when
        List<String> row = serializer.serialize(null);

        // then
        assertNotNull(row);
        assertEquals(0, row.size());
    }

    @Test
    void shouldThrowOnUnknownFieldType() {
        // given
        var serializer = new BeanCsvSerializer(new StandardConverterRegistry());

        assertThrows(RuntimeException.class, () ->
                serializer.serialize(new UnknownTypeCsvRecord())
        );
    }

}
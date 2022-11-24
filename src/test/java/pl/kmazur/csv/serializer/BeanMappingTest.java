package pl.kmazur.csv.serializer;

import org.junit.jupiter.api.Test;
import pl.kmazur.csv.annotation.CsvCustomConverter;
import pl.kmazur.csv.annotation.CsvIndexedField;
import pl.kmazur.csv.converter.DoubleConverter;
import pl.kmazur.csv.converter.registry.ConverterRegistry;
import pl.kmazur.csv.converter.registry.StandardConverterRegistry;
import pl.kmazur.csv.deserializer.BeanCsvDeserializer;
import pl.kmazur.csv.deserializer.DefaultConstructorInstanceFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class BeanMappingTest {

    public static class CsvRecord {
        public CsvRecord() {
        }

        @CsvIndexedField(0)
        int id;
        @CsvIndexedField(1)
        String title;
        @CsvIndexedField(2)
        Long longField;
        @CsvIndexedField(3)
        boolean booleanField;

        @CsvIndexedField(4)
        String nullField;

        @CsvCustomConverter(DoubleConverter.class)
        @CsvIndexedField(5)
        Object customField;

        String unusedField;
    }

    @Test
    void shouldSerializeAndDeserializeCsv() {
        // given
        ConverterRegistry registry = new StandardConverterRegistry();
        registry.addCustomConverter(new DoubleConverter());
        var ser = new BeanCsvSerializer(registry);
        var deser = new BeanCsvDeserializer(registry, new DefaultConstructorInstanceFactory());

        CsvRecord record = new CsvRecord();
        record.id = 1;
        record.title = "title";
        record.longField = 5L;
        record.booleanField = true;
        record.nullField = null;
        record.customField = 1.23;
        record.unusedField = "unused";

        // when
        List<String> row = ser.serialize(record);
        CsvRecord obj = deser.deserialize(row, CsvRecord.class);

        // then
        assertEquals(record.id, obj.id);
        assertEquals(record.title, obj.title);
        assertEquals(record.longField, obj.longField);
        assertEquals(record.booleanField, obj.booleanField);
        assertEquals(record.nullField, obj.nullField);
        assertNotNull(record.customField);
        assertEquals(Double.class, record.customField.getClass());
        assertEquals(record.customField, obj.customField);
        assertNull(obj.unusedField);

    }

}
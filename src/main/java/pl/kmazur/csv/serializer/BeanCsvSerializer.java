package pl.kmazur.csv.serializer;

import pl.kmazur.csv.annotation.CsvCustomConverter;
import pl.kmazur.csv.annotation.CsvIndexedField;
import pl.kmazur.csv.converter.Converter;
import pl.kmazur.csv.converter.ConverterNotFoundException;
import pl.kmazur.csv.converter.registry.ConverterRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BeanCsvSerializer implements CsvSerializer {

    private final ConverterRegistry converterRegistry;

    public BeanCsvSerializer(ConverterRegistry converterRegistry) {
        this.converterRegistry = converterRegistry;
    }

    @Override
    public <T> List<String> serialize(T object) {
        if (object == null) {
            return List.of();
        }

        ArrayList<String> result = new ArrayList<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            CsvIndexedField indexedField = field.getAnnotation(CsvIndexedField.class);
            if (indexedField != null) {
                handleField(result, object, field, indexedField);
            }
        }
        return result;
    }

    private <T> void handleField(ArrayList<String> row, T object, Field field, CsvIndexedField indexedField) {
        try {
            int index = indexedField.value();
            row.ensureCapacity(index + 1);

            Converter<Object, String> converter = getConverter(field);

            field.setAccessible(true);
            Object value = field.get(object);
            String str = converter.apply(value);
            row.add(str);
        } catch (IllegalAccessException e) {
            throw new CsvSerializationException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private Converter<Object, String> getConverter(Field field) {
        CsvCustomConverter customConverter = field.getAnnotation(CsvCustomConverter.class);
        if (customConverter != null) {
            Class<? extends Converter<?, ?>> converterType = customConverter.value();
            Converter<?, ?> converter = converterRegistry.getCustomConverterOfType(converterType);
            if (converter == null) {
                throw new ConverterNotFoundException(field.getDeclaringClass(), field.getName(), field.getType());
            }
            return (Converter<Object, String>) converter;
        }

        Converter<?, String> converter = converterRegistry.getConverter(field.getType());
        if (converter == null) {
            throw new ConverterNotFoundException(field.getDeclaringClass(), field.getName(), field.getType());
        }
        return (Converter<Object, String>) converter;
    }
}

package pl.kmazur.csv.deserializer;

import pl.kmazur.csv.annotation.CsvCustomConverter;
import pl.kmazur.csv.annotation.CsvIndexedField;
import pl.kmazur.csv.converter.Converter;
import pl.kmazur.csv.converter.ConverterNotFoundException;
import pl.kmazur.csv.converter.registry.ConverterRegistry;

import java.lang.reflect.Field;
import java.util.List;

public class BeanCsvDeserializer implements CsvDeserializer {

    private final ConverterRegistry converterRegistry;
    private final InstanceFactory instanceFactory;

    public BeanCsvDeserializer(ConverterRegistry converterRegistry, InstanceFactory instanceFactory) {
        this.converterRegistry = converterRegistry;
        this.instanceFactory = instanceFactory;
    }

    @Override
    public <T> T deserialize(List<String> row, Class<T> type) {
        T obj = instanceFactory.createInstance(type);
        return initializeInstance(row, obj);
    }

    private <T> T initializeInstance(List<String> row, T obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            CsvIndexedField indexedField = field.getAnnotation(CsvIndexedField.class);
            if (indexedField != null) {
                handleField(obj, row, field, indexedField);
            }
        }
        return obj;
    }

    private <T> void handleField(T obj, List<String> row, Field field, CsvIndexedField indexedField) {
        try {
            int index = indexedField.value();
            if (index >= 0 && index < row.size()) {
                String str = row.get(index);

                Converter<Object, String> converter = getConverter(field);
                Object value = converter.unapply(str);

                field.setAccessible(true);
                field.set(obj, value);
            }
        } catch (IllegalAccessException e) {
            throw new CsvDeserializationException(e);
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

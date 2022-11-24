package pl.kmazur.csv.converter.registry;

import pl.kmazur.csv.converter.Converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MapConverterRegistry implements ConverterRegistry {

    private final Map<Class<?>, Converter<?, ?>> converters;
    private final List<Converter<?, ?>> customConverters;

    public MapConverterRegistry() {
        this.converters = new HashMap<>();
        this.customConverters = new ArrayList<>();
    }

    @Override
    public <T> void addCustomConverter(Converter<T, String> converter) {
        Objects.requireNonNull(converter);
        this.customConverters.add(converter);
    }

    @Override
    public <T> void addConverter(Class<T> type, Converter<T, String> converter) {
        converters.put(type, converter);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Converter<T, String> getConverter(Class<T> type) {
        return (Converter<T, String>) converters.get(type);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Converter<?, ?>> T getCustomConverterOfType(Class<T> type) {
        List<Converter<?, ?>> filtered = customConverters.stream()
                .filter(c -> c.getClass().equals(type))
                .toList();

        int size = filtered.size();
        if (size > 1) {
            throw new IllegalStateException("Found " + size + " converters of type: " + type + " - ambiguous result");
        }

        return filtered.isEmpty() ? null : (T) filtered.get(0);
    }


}

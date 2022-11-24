package pl.kmazur.csv.converter.registry;

import pl.kmazur.csv.converter.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompositeConverterRegistry implements ConverterRegistry {
    private final List<ConverterRegistry> registryList;
    private final MapConverterRegistry internalRegistry;

    public CompositeConverterRegistry(List<ConverterRegistry> registryList) {
        this.registryList = new ArrayList<>(registryList);
        this.internalRegistry = new MapConverterRegistry();

        this.registryList.add(0, internalRegistry);
    }

    @Override
    public <T> void addCustomConverter(Converter<T, String> converter) {
        internalRegistry.addCustomConverter(converter);
    }

    @Override
    public <T> void addConverter(Class<T> type, Converter<T, String> converter) {
        internalRegistry.addConverter(type, converter);
    }

    @Override
    public <T> Converter<T, String> getConverter(Class<T> type) {
        for (ConverterRegistry registry : this.registryList) {
            Converter<T, String> converter = registry.getConverter(type);
            if (converter != null) {
                return converter;
            }
        }
        return null;
    }

    @Override
    public <T extends Converter<?, ?>> T getCustomConverterOfType(Class<T> type) {
        List<T> filtered = registryList.stream()
                .map(r -> r.getCustomConverterOfType(type))
                .filter(Objects::nonNull)
                .toList();

        int size = filtered.size();
        if (size > 1) {
            throw new IllegalStateException("Found " + size + " converters of type: " + type + " - ambiguous result");
        }
        return filtered.isEmpty() ? null : filtered.get(0);
    }
}

package pl.kmazur.csv.converter.registry;

import pl.kmazur.csv.converter.Converter;

public interface ConverterRegistry {

    <T> void addCustomConverter(Converter<T, String> converter);

    <T> void addConverter(Class<T> type, Converter<T, String> converter);

    <T> Converter<T, String> getConverter(Class<T> type);

    <T extends Converter<?, ?>> T getCustomConverterOfType(Class<T> type);

}

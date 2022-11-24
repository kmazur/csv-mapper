package pl.kmazur.csv.converter.registry;

import pl.kmazur.csv.converter.ByteConverter;
import pl.kmazur.csv.converter.CharacterConverter;
import pl.kmazur.csv.converter.DoubleConverter;
import pl.kmazur.csv.converter.FloatConverter;
import pl.kmazur.csv.converter.IntegerConverter;
import pl.kmazur.csv.converter.LongConverter;
import pl.kmazur.csv.converter.ShortConverter;
import pl.kmazur.csv.converter.StringConverter;

public class JavaSimpleTypesConverterRegistry extends MapConverterRegistry {

    public JavaSimpleTypesConverterRegistry() {
        addConverter(Byte.class, new ByteConverter());
        addConverter(Short.class, new ShortConverter());
        addConverter(Character.class, new CharacterConverter());
        addConverter(Integer.class, new IntegerConverter());
        addConverter(Long.class, new LongConverter());
        addConverter(Float.class, new FloatConverter());
        addConverter(Double.class, new DoubleConverter());
        addConverter(String.class, new StringConverter());
    }

}

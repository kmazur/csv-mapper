package pl.kmazur.csv.converter.registry;

import pl.kmazur.csv.converter.BooleanConverter;
import pl.kmazur.csv.converter.ByteConverter;
import pl.kmazur.csv.converter.CharacterConverter;
import pl.kmazur.csv.converter.DoubleConverter;
import pl.kmazur.csv.converter.FloatConverter;
import pl.kmazur.csv.converter.IntegerConverter;
import pl.kmazur.csv.converter.ShortConverter;

public class PrimitiveConverterRegistry extends MapConverterRegistry {

    public PrimitiveConverterRegistry() {
        addConverter(byte.class, new ByteConverter());
        addConverter(short.class, new ShortConverter());
        addConverter(char.class, new CharacterConverter());
        addConverter(int.class, new IntegerConverter());
        addConverter(float.class, new FloatConverter());
        addConverter(double.class, new DoubleConverter());
        addConverter(boolean.class, new BooleanConverter());
    }

}

package pl.kmazur.csv.converter.registry;

import org.junit.jupiter.api.Test;
import pl.kmazur.csv.converter.Converter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompositeConverterRegistryTest {

    @Test
    void shouldOverrideConverter() {
        // given
        var registry = new CompositeConverterRegistry(
                List.of(new PrimitiveConverterRegistry(), new JavaSimpleTypesConverterRegistry())
        );

        Converter<Integer, String> customConverter = Converter.from(
                i -> (i + 1) + "",
                s -> Integer.parseInt(s) - 1
        );

        int exampleValue = 1;

        // when
        registry.addConverter(Integer.class, customConverter);

        Converter<Integer, String> converter = registry.getConverter(Integer.class);

        String str = converter.apply(exampleValue);
        Integer result = converter.unapply(str);

        assertEquals("2", str);
        assertEquals(result, exampleValue);
    }

}
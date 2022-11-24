package pl.kmazur.csv.converter.registry;

import java.util.List;

public class StandardConverterRegistry extends CompositeConverterRegistry {

    public StandardConverterRegistry() {
        super(List.of(
                new JavaSimpleTypesConverterRegistry(),
                new PrimitiveConverterRegistry()
        ));
    }

}

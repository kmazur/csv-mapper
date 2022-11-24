package pl.kmazur.csv.converter;

public class ShortConverter implements Converter<Short, String> {

    @Override
    public String apply(Short obj) {
        return obj.toString();
    }

    @Override
    public Short unapply(String s) {
        return Short.parseShort(s);
    }

}

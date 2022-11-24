package pl.kmazur.csv.converter;

public class BooleanConverter implements Converter<Boolean, String> {

    @Override
    public String apply(Boolean obj) {
        return obj.toString();
    }

    @Override
    public Boolean unapply(String obj) {
        return Boolean.parseBoolean(obj);
    }
}

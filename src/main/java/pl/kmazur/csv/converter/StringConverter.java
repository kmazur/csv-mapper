package pl.kmazur.csv.converter;

public class StringConverter implements Converter<String, String> {

    @Override
    public String apply(String s) {
        return s;
    }

    @Override
    public String unapply(String obj) {
        return obj;
    }

}

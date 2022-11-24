package pl.kmazur.csv.converter;

public class IntegerConverter implements Converter<Integer, String> {

    @Override
    public String apply(Integer obj) {
        return obj.toString();
    }

    @Override
    public Integer unapply(String obj) {
        return Integer.parseInt(obj);
    }
}

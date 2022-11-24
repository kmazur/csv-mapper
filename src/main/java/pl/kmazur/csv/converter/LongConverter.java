package pl.kmazur.csv.converter;

public class LongConverter implements Converter<Long, String> {

    @Override
    public String apply(Long obj) {
        return obj.toString();
    }

    @Override
    public Long unapply(String obj) {
        return Long.parseLong(obj);
    }
}

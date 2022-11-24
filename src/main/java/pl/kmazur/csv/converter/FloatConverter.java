package pl.kmazur.csv.converter;

public class FloatConverter implements Converter<Float, String> {

    @Override
    public String apply(Float obj) {
        return obj.toString();
    }

    @Override
    public Float unapply(String obj) {
        return Float.parseFloat(obj);
    }
}

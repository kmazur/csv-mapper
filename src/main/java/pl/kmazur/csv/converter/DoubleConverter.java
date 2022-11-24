package pl.kmazur.csv.converter;

public class DoubleConverter implements Converter<Double, String> {

    @Override
    public String apply(Double obj) {
        return obj.toString();
    }

    @Override
    public Double unapply(String obj) {
        return Double.parseDouble(obj);
    }
}

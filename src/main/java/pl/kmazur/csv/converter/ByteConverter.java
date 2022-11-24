package pl.kmazur.csv.converter;

public class ByteConverter implements Converter<Byte, String> {

    @Override
    public String apply(Byte obj) {
        return obj.toString();
    }

    @Override
    public Byte unapply(String obj) {
        return Byte.valueOf(obj);
    }
}

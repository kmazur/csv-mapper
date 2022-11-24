package pl.kmazur.csv.converter;

public class CharacterConverter implements Converter<Character, String> {

    @Override
    public String apply(Character obj) {
        return obj.toString();
    }

    @Override
    public Character unapply(String obj) {
        return obj.charAt(0);
    }
}

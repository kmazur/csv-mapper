package pl.kmazur.csv.io.element;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NullAsEmptyStringConverterTest {

    @Test
    void shouldConvertToNullOnEmptyString() {
        // given
        var converter = new NullAsEmptyStringConverter();

        // when
        var result = converter.apply(null);

        // then
        assertEquals("", result);
    }

    @Test
    void shouldNotConvertTestString() {
        // given
        var converter = new NullAsEmptyStringConverter();
        String value = "test";

        // when
        var result = converter.apply(value);

        // then
        assertEquals(value, result);
    }
}
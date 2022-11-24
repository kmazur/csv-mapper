package pl.kmazur.csv.io.element;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BlankStringAsNullElementConverterTest {

    @Test
    void shouldNotConvertNullValue() {
        // given
        var converter = new BlankStringAsNullElementConverter();

        // when
        var result = converter.apply(null);

        // then
        assertNull(result);
    }

    @Test
    void shouldConvertToNullEmptyString() {
        // given
        var converter = new BlankStringAsNullElementConverter();
        String value = "";

        // when
        var result = converter.apply(value);

        // then
        assertNull(result);
    }


    @Test
    void shouldConvertToNullSingleSpaceString() {
        // given
        var converter = new BlankStringAsNullElementConverter();
        String value = " ";

        // when
        var result = converter.apply(value);

        // then
        assertNull(result);
    }

    @Test
    void shouldConvertToNullOnlyWhitespacedString() {
        // given
        var converter = new BlankStringAsNullElementConverter();
        String value = " \t\r \f ";

        // when
        var result = converter.apply(value);

        // then
        assertNull(result);
    }

    @Test
    void shouldNotConvertNotBlankString() {
        // given
        var converter = new BlankStringAsNullElementConverter();
        String value = "test";

        // when
        var result = converter.apply(value);

        // then
        assertEquals(value, result);
    }

}
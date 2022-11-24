package pl.kmazur.csv.io.element;

import org.junit.jupiter.api.Test;
import pl.kmazur.csv.io.tokenizer.CsvFormatSettings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EscapeElementConverterTest {

    private final CsvFormatSettings settings = new CsvFormatSettings('\\', '"', ',');

    @Test
    void shouldNotConvertNullValue() {
        // given
        var converter = new EscapeElementConverter(settings.getEscape());

        // when
        var result = converter.apply(null);

        // then
        assertNull(result);
    }

    @Test
    void shouldConvertToEmptyString() {
        // given
        var converter = new EscapeElementConverter(settings.getEscape());
        String value = "";

        // when
        var result = converter.apply(value);

        // then
        assertEquals("", result);
    }


    @Test
    void shouldNotEscapeString() {
        // given
        var converter = new EscapeElementConverter(settings.getEscape());
        String value = " Abc,.123][ ";

        // when
        var result = converter.apply(value);

        // then
        assertEquals(value, result);
    }

    @Test
    void shouldEscapeSpecialChars() {
        // given
        var converter = new EscapeElementConverter(settings.getEscape());
        String value = "\t \n \f \r \\ \" \b";

        // when
        var result = converter.apply(value);

        // then
        assertEquals("\\t \\n \\f \\r \\\\ \\\" \\b", result);
    }


}
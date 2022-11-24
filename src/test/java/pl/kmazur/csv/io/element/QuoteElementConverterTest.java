package pl.kmazur.csv.io.element;

import org.junit.jupiter.api.Test;
import pl.kmazur.csv.io.tokenizer.CsvFormatSettings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class QuoteElementConverterTest {

    @Test
    void shouldQuoteValue() {
        // given
        CsvFormatSettings settings = new CsvFormatSettings();
        var converter = new QuoteElementConverter(settings.getQuote());
        String value = "test";

        // when
        String quoted = converter.apply(value);

        // then
        assertEquals("\"" + value + "\"", quoted);
    }

    @Test
    void shouldNotQuoteNullValue() {
        // given
        CsvFormatSettings settings = new CsvFormatSettings();
        var converter = new QuoteElementConverter(settings.getQuote());

        // when
        String quoted = converter.apply(null);

        // then
        assertNull(quoted);
    }

}
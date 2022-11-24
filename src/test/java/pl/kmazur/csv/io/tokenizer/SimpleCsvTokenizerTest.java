package pl.kmazur.csv.io.tokenizer;

import org.junit.jupiter.api.Test;
import pl.kmazur.csv.io.ReaderInputSource;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleCsvTokenizerTest {

    @Test
    void shouldReturnEofImmediatelyOnEmptyStream() {
        // given
        try (ReaderInputSource source = new ReaderInputSource(
                new InputStreamReader(new ByteArrayInputStream(new byte[0])))
        ) {
            var tokenizer = new SimpleCsvTokenizer(source, new CsvFormatSettings());

            // when
            CsvToken token = tokenizer.getNextToken();

            // then
            assertEquals(CsvToken.EOF, token);
        }
    }

    @Test
    void shouldReadCorrectlySimpleCsv() {
        // given
        var csv = "\"id\",\"title\"\n" +
                "\"1\",\"some title\"";

        try (ReaderInputSource source = new ReaderInputSource(new StringReader(csv))) {
            var tokenizer = new SimpleCsvTokenizer(source, new CsvFormatSettings('\\', '\"', ',', "\n"));

            // then
            assertEquals(CsvToken.VALUE, tokenizer.getNextToken());
            assertEquals(CsvToken.VALUE_SEPARATOR, tokenizer.getNextToken());
            assertEquals(CsvToken.VALUE, tokenizer.getNextToken());
            assertEquals(CsvToken.ROW_SEPARATOR, tokenizer.getNextToken());
            assertEquals(CsvToken.VALUE, tokenizer.getNextToken());
            assertEquals(CsvToken.VALUE_SEPARATOR, tokenizer.getNextToken());
            assertEquals(CsvToken.VALUE, tokenizer.getNextToken());
            assertEquals(CsvToken.EOF, tokenizer.getNextToken());
        }
    }

}
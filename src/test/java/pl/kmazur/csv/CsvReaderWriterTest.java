package pl.kmazur.csv;

import org.junit.jupiter.api.Test;
import pl.kmazur.csv.io.InputSource;
import pl.kmazur.csv.io.OutputSource;
import pl.kmazur.csv.io.ReaderInputSource;
import pl.kmazur.csv.io.WriterOutputSource;
import pl.kmazur.csv.io.reader.SimpleCsvReader;
import pl.kmazur.csv.io.tokenizer.CsvFormatSettings;
import pl.kmazur.csv.io.writer.SimpleCsvWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvReaderWriterTest {

    @Test
    void shouldReadAndWriteCsv() throws IOException {
        // given
        CsvFormatSettings formatSettings = new CsvFormatSettings();
        List<List<String>> rows = readCsv("/example.csv", formatSettings);

        // when
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (OutputSource output = new WriterOutputSource(new OutputStreamWriter(baos, StandardCharsets.UTF_8))) {
            var writer = new SimpleCsvWriter(output, formatSettings);
            writer.writeRows(rows);
        }

        String writtenContents = baos.toString(StandardCharsets.UTF_8);
        String expected = readResource("/example-expected.csv");

        assertEquals(expected, writtenContents);
    }

    private String readResource(String resource) {
        try (InputStream in = this.getClass().getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalArgumentException("Resource not found: " + resource);
            }
            byte[] bytes = in.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private List<List<String>> readCsv(String resource, CsvFormatSettings formatSettings) {
        String str = readResource(resource);

        try (InputSource input = new ReaderInputSource(new StringReader(str))) {
            var reader = new SimpleCsvReader(input, formatSettings);
            return reader.readAll();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
package pl.kmazur.csv.io;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

public class WriterOutputSource implements OutputSource {

    private final Writer writer;

    public WriterOutputSource(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void write(String str) {
        try {
            writer.write(str);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

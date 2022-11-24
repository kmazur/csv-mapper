package pl.kmazur.csv.io;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;

public class ReaderInputSource implements InputSource {

    private final Reader reader;

    public ReaderInputSource(Reader reader) {
        this.reader = reader;
    }

    @Override
    public int read() {
        try {
            return reader.read();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

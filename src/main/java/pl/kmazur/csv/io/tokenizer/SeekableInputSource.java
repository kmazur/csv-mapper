package pl.kmazur.csv.io.tokenizer;

import pl.kmazur.csv.io.InputSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class SeekableInputSource implements InputSource {

    private final InputSource delegate;

    private final List<Integer> readBuffer = new ArrayList<>();
    private int peekIndex;

    public SeekableInputSource(InputSource delegate) {
        this.delegate = delegate;
    }

    public void resetPeek() {
        peekIndex = 0;
    }

    public int peekRead() {
        if (peekIndex < readBuffer.size()) {
            return readBuffer.get(peekIndex);
        }
        int read = delegate.read();
        ++peekIndex;
        readBuffer.add(read);
        return read;
    }

    @Override
    public int read() {
        if (readBuffer.isEmpty()) {
            return delegate.read();
        } else {
            peekIndex = Math.max(0, peekIndex - 1);
            return readBuffer.remove(0);
        }
    }

    @Override
    public void close() throws IOException {
        delegate.close();
    }

    public void skip(int count) {
        for (int i = 0; i < count; ++i) {
            read();
        }
    }
}

package pl.kmazur.csv.io;

import java.io.Closeable;

public interface InputSource extends Closeable {
    int read();

}

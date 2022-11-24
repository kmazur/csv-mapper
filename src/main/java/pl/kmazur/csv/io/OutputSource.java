package pl.kmazur.csv.io;

import java.io.Closeable;

public interface OutputSource extends Closeable {
    void write(String str);

}

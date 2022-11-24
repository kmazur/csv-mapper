package pl.kmazur.csv.io.writer;

import java.util.Arrays;
import java.util.List;

public interface CsvWriter {

    void writeRow(List<String> row);

    default void writeRow(String[] row) {
        writeRow(Arrays.asList(row));
    }

    default void writeRows(List<String>... rows) {
        for (List<String> row : rows) {
            writeRow(row);
        }
    }

    default void writeRows(List<List<String>> rows) {
        for (List<String> row : rows) {
            writeRow(row);
        }
    }

}

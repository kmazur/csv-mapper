package pl.kmazur.csv.io.reader;

import java.util.ArrayList;
import java.util.List;

public interface CsvReader extends Iterable<List<String>> {

    default List<List<String>> readAll() {
        List<List<String>> result = new ArrayList<>();
        for (List<String> row : this) {
            result.add(row);
        }
        return result;
    }

}

package pl.kmazur.csv.deserializer;

import java.util.ArrayList;
import java.util.List;

public interface CsvDeserializer {

    <T> T deserialize(List<String> row, Class<T> type);

    default <T> List<T> deserializeAll(List<List<String>> rows, Class<T> type) {
        List<T> result = new ArrayList<>();
        for (List<String> row : rows) {
            result.add(deserialize(row, type));
        }
        return result;
    }

}

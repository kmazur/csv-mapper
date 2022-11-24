package pl.kmazur.csv.serializer;

import java.util.List;

public interface CsvSerializer {

    <T> List<String> serialize(T object);

}

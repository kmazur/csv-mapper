package pl.kmazur.csv.deserializer;

public interface InstanceFactory {

    <T> T createInstance(Class<T> type);

}

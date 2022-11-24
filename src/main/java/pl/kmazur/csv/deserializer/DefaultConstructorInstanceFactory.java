package pl.kmazur.csv.deserializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DefaultConstructorInstanceFactory implements InstanceFactory {

    @Override
    public <T> T createInstance(Class<T> type) {
        try {
            Constructor<T> constructor = type.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new NoDefaultConstructorFoundException(e);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new CsvDeserializationException(e);
        }
    }

}

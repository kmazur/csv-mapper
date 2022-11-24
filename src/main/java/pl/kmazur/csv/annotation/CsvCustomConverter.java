package pl.kmazur.csv.annotation;

import pl.kmazur.csv.converter.Converter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvCustomConverter {
    Class<? extends Converter<?, ?>> value();
}

package pl.kmazur.csv.converter;

import java.util.function.Function;

public interface Converter<F, T> {

    T apply(F obj);

    F unapply(T obj);

    default Function<F, T> asForward() {
        return this::apply;
    }

    default Function<T, F> asBackward() {
        return this::unapply;
    }

    default <R> Converter<F, R> andThen(Converter<T, R> after) {
        return from(
                (F f) -> after.apply(this.apply(f)),
                (R r) -> this.unapply(after.unapply(r))
        );
    }

    default <R> Converter<R, T> compose(Converter<R, F> before) {
        return from(
                (R r) -> this.apply(before.apply(r)),
                (T t) -> before.unapply(this.unapply(t))
        );
    }

    static <T> Converter<T, T> identity() {
        return from(Function.identity(), Function.identity());
    }

    static <F, T> Converter<F, T> from(Function<F, T> applyFunc, Function<T, F> unapplyFunc) {
        return new Converter<>() {
            @Override
            public T apply(F obj) {
                return applyFunc.apply(obj);
            }

            @Override
            public F unapply(T obj) {
                return unapplyFunc.apply(obj);
            }
        };
    }

}

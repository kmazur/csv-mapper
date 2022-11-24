package pl.kmazur.csv.converter;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConverterTest {

    @Test
    void shouldComposeConverter() {
        // given
        Converter<Integer, String> intStrConverter = Converter.from(
                Object::toString,
                Integer::parseInt
        );

        Converter<List<Integer>, Integer> listIntConverter = Converter.from(
                List::size,
                i -> IntStream.rangeClosed(1, i).boxed().toList()
        );

        List<Integer> list = List.of(1, 2, 3);
        String countStr = "3";

        // when
        Integer resultInt = listIntConverter.apply(list);
        String resultStr = intStrConverter.apply(resultInt);

        Integer resultInt2 = intStrConverter.unapply(resultStr);
        List<Integer> resultList2 = listIntConverter.unapply(resultInt2);

        Converter<List<Integer>, String> composed = intStrConverter.compose(listIntConverter);
        String converterStr = composed.apply(list);
        List<Integer> composedList = composed.unapply(converterStr);

        // then
        assertEquals(countStr, resultStr);
        assertEquals(resultStr, converterStr);
        assertEquals(list, resultList2);
        assertEquals(resultList2, composedList);
    }

    @Test
    void shouldCombineConverter() {
        // given
        Converter<Integer, String> intStrConverter = Converter.from(
                Object::toString,
                Integer::parseInt
        );

        Converter<List<Integer>, Integer> listIntConverter = Converter.from(
                List::size,
                i -> IntStream.rangeClosed(1, i).boxed().toList()
        );

        List<Integer> list = List.of(1, 2, 3);
        String countStr = "3";

        // when
        Integer resultInt = listIntConverter.apply(list);
        String resultStr = intStrConverter.apply(resultInt);

        Integer resultInt2 = intStrConverter.unapply(resultStr);
        List<Integer> resultList2 = listIntConverter.unapply(resultInt2);

        Converter<List<Integer>, String> combined = listIntConverter.andThen(intStrConverter);
        String converterStr = combined.apply(list);
        List<Integer> composedList = combined.unapply(converterStr);

        // then
        assertEquals(countStr, resultStr);
        assertEquals(resultStr, converterStr);
        assertEquals(list, resultList2);
        assertEquals(resultList2, composedList);
    }

}
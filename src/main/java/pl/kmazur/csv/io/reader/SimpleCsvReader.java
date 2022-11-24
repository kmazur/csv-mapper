package pl.kmazur.csv.io.reader;

import pl.kmazur.csv.io.InputSource;
import pl.kmazur.csv.io.element.BlankStringAsNullElementConverter;
import pl.kmazur.csv.io.element.TrimElementConverter;
import pl.kmazur.csv.io.element.UnescapeElementConverter;
import pl.kmazur.csv.io.element.UnquoteElementConverter;
import pl.kmazur.csv.io.tokenizer.CsvFormatSettings;
import pl.kmazur.csv.io.tokenizer.CsvToken;
import pl.kmazur.csv.io.tokenizer.SimpleCsvTokenizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class SimpleCsvReader implements CsvReader {

    private final SimpleCsvTokenizer tokenizer;
    private final Function<String, String> elementConverter;
    private final CsvIterator iterator;

    public SimpleCsvReader(InputSource source, CsvFormatSettings settings) {
        this(source, settings,
                new TrimElementConverter()
                        .andThen(new UnquoteElementConverter(settings))
                        .andThen(new UnescapeElementConverter(settings))
                        .andThen(new BlankStringAsNullElementConverter())
        );
    }

    public SimpleCsvReader(InputSource source, CsvFormatSettings settings, Function<String, String> elementConverter) {
        this.tokenizer = new SimpleCsvTokenizer(source, settings);
        this.elementConverter = elementConverter;
        this.iterator = new CsvIterator();
    }

    @Override
    public List<List<String>> readAll() {
        List<List<String>> result = new ArrayList<>();
        for (List<String> row : this) {
            result.add(row);
        }
        return result;
    }

    private class CsvIterator implements Iterator<List<String>> {
        private CsvToken currentToken;
        private boolean prevIsValueToken;

        @Override
        public boolean hasNext() {
            if (currentToken == null) {
                currentToken = tokenizer.getNextToken();
            }
            return currentToken != CsvToken.EOF;
        }

        @Override
        public List<String> next() {
            if (currentToken == CsvToken.EOF) {
                throw new NoSuchElementException();
            }

            List<String> rowValues = new ArrayList<>();
            CsvToken prevToken;
            do {
                if (currentToken == CsvToken.VALUE) {
                    rowValues.add(getTokenValue(tokenizer.getTokenValue()));
                    prevIsValueToken = true;
                } else if (currentToken == CsvToken.VALUE_SEPARATOR) {
                    if (prevIsValueToken) {
                        prevIsValueToken = false;
                    } else {
                        rowValues.add(getTokenValue(null));
                    }
                }
                prevToken = currentToken;
            }
            while ((currentToken = tokenizer.getNextToken()) != CsvToken.EOF && currentToken != CsvToken.ROW_SEPARATOR);

            if (prevToken == CsvToken.VALUE_SEPARATOR) {
                // csv row ending with value separator
                rowValues.add(getTokenValue(null));
            }

            return rowValues;
        }

        private String getTokenValue(String tokenValue) {
            return elementConverter.apply(tokenValue);
        }
    }

    @Override
    public Iterator<List<String>> iterator() {
        // single-pass only
        return iterator;
    }
}

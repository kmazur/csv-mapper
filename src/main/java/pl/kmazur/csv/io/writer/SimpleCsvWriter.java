package pl.kmazur.csv.io.writer;

import pl.kmazur.csv.io.OutputSource;
import pl.kmazur.csv.io.element.EscapeElementConverter;
import pl.kmazur.csv.io.element.NullAsEmptyStringConverter;
import pl.kmazur.csv.io.element.QuoteElementConverter;
import pl.kmazur.csv.io.tokenizer.CsvFormatSettings;

import java.util.List;
import java.util.function.Function;

public class SimpleCsvWriter implements CsvWriter {

    private final OutputSource output;
    private final CsvFormatSettings settings;
    private final Function<String, String> writerConverter;
    private boolean hasWritten;

    public SimpleCsvWriter(OutputSource output, CsvFormatSettings settings) {
        this(output, settings, new EscapeElementConverter(settings.getEscape())
                .andThen(new NullAsEmptyStringConverter())
                .andThen(new QuoteElementConverter(settings.getQuote())));
    }

    public SimpleCsvWriter(OutputSource output, CsvFormatSettings settings, Function<String, String> writerConverter) {
        this.output = output;
        this.settings = settings;
        this.writerConverter = writerConverter;
    }

    @Override
    public void writeRow(List<String> row) {
        if (row == null || row.isEmpty()) {
            return;
        }

        if (!hasWritten) {
            hasWritten = true;
        } else {
            output.write(settings.getRowSeparator());
        }

        for (int i = 0; i < row.size(); ++i) {
            String value = row.get(i);
            if (i != 0) {
                output.write(settings.getValueSeparatorAsString());
            }
            output.write(writerConverter.apply(value));
        }
    }
}

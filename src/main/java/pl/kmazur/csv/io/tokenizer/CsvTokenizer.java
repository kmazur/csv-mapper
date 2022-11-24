package pl.kmazur.csv.io.tokenizer;

public interface CsvTokenizer {

    CsvToken getCurrentToken();

    CsvToken getNextToken();

    String getTokenValue();

}

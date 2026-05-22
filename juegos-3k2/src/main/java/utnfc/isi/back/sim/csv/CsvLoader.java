package utnfc.isi.back.sim.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.opencsv.CSVParser;

public final class CsvLoader {
  private CsvLoader(){}

  public static List<CsvGameRow> read(String path) throws Exception {
    try (var fr = new FileReader(path, StandardCharsets.UTF_8)) {
      return new CsvToBeanBuilder<CsvGameRow>(fr)
          .withType(CsvGameRow.class)
          .withSeparator(';')
          .withIgnoreQuotations(true)
          .withQuoteChar(CSVParser.NULL_CHARACTER)
          .withIgnoreLeadingWhiteSpace(true)
          .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
          .build()
          .parse();
    }
  }
}

package utnfc.isi.back.sim.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CsvGameRow {

  @CsvBindByName(column = "Title")
  private String title;

  @CsvCustomBindByName(column = "Release_Date", converter = DateToEpochIntConverter.class)
  private Integer releaseDateEpoch;   // null si "TBD" o vacío

  @CsvBindByName(column = "Developers")
  private String developers;          // ejemplos: "['Nintendo']" | "[]" | ""

  @CsvBindByName(column = "Summary")
  private String summary;

  @CsvBindByName(column = "Platforms")
  private String platforms;           // "['Nintendo Switch']" | "[]" | ""

  @CsvBindByName(column = "Genres")
  private String genres;              // "['Adventure']" | "[]" | ""

  @CsvCustomBindByName(column = "Rating", converter = RatingConverter.class)
  private Double rating;              // null si "N/A" o vacío

  @CsvCustomBindByName(column = "Plays", converter = KNumberConverter.class)
  private Integer plays;              // "4.3K" → 4300, "" → null

  @CsvCustomBindByName(column = "Playing", converter = KNumberConverter.class)
  private Integer playing;

  @CsvBindByName(column = "esrb_rating")
  private String esrbCode;            // "E", "E10+", "" → null luego
}


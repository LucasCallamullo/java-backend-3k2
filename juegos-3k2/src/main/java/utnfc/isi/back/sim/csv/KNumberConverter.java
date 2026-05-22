package utnfc.isi.back.sim.csv;

import com.opencsv.bean.AbstractBeanField; import java.util.Locale;

public class KNumberConverter extends AbstractBeanField<Integer, String> {
  @Override protected Integer convert(String v) {
    if (v == null) return null;
    v = v.trim();
    if (v.isEmpty()) return null;
    var up = v.toUpperCase(Locale.ROOT);
    if (up.endsWith("K")) {
      double base = Double.parseDouble(up.substring(0, up.length()-1).replace(",", "."));
      return (int) Math.round(base * 1000);
    }
    return Integer.valueOf(up);
  }
}


package utnfc.isi.back.sim.csv;
import com.opencsv.bean.AbstractBeanField;

public class RatingConverter extends AbstractBeanField<Double, String> {
  @Override protected Double convert(String v) {
    if (v == null) return null;
    v = v.trim();
    if (v.isEmpty() || v.equalsIgnoreCase("N/A")) return null;
    return Double.valueOf(v.replace(",", "."));
  }
}


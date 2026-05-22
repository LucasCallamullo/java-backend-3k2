package utnfc.isi.back.sim.csv;
import com.opencsv.bean.AbstractBeanField;
import java.text.SimpleDateFormat; import java.util.Locale;

public class DateToEpochIntConverter extends AbstractBeanField<Integer, String> {
  private static final SimpleDateFormat FMT = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
  @Override protected Integer convert(String v) {
    if (v == null) return null;
    v = v.trim();
    if (v.isEmpty() || v.equalsIgnoreCase("TBD")) return null;
    try { return Math.toIntExact(FMT.parse(v).getTime()); } catch (Exception e) { return null; }
  }
}


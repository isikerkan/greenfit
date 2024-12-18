package ch.pa5.greenfit.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.val;

public class DateUtils {
  private DateUtils() {}

  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd / MM / yyyy");

  public static String computeDateLabel(LocalDate date) {
    val yesterday = LocalDate.now().minusDays(1);
    return date.isEqual(yesterday) ? "Gestern" : date.format(formatter);
  }
}

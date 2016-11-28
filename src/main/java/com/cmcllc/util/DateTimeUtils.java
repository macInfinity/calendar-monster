package com.cmcllc.util;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

/**
 * Created by chrismaki on 11/27/16.
 */
public class DateTimeUtils {
  private DateTimeUtils() {}

  /**
   * 230101 - 11:01:01 PM
   * 225559 - 10:55:59 PM
   * 011513 - 1:11:13 AM
   */
  public static final DateTimeFormatter BASIC_TIME = DateTimeFormatter.ofPattern("Hms");

  public static String formatLocalDateTime(LocalDateTime localDateTime) {
    return String.format("%sT%s", BASIC_ISO_DATE.format(localDateTime),
        BASIC_TIME.format(localDateTime));
  }

  public static DateTime toiCalDateTime(LocalDateTime localDateTime) {
    java.util.Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

    return new DateTime(date);
  }
  public static Date toiCalDate(LocalDateTime localDateTime) {
    java.util.Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

    return new net.fortuna.ical4j.model.Date(date);
  }
}

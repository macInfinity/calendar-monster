package com.cmcllc.util;

import com.google.common.base.Preconditions;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
  public static final DateTimeFormatter BASIC_TIME = DateTimeFormatter.ofPattern("HHmmss");
//  public static final DateTimeFormatter BASIC_TIME = DateTimeFormatter.ofPattern("Hms");

  static String formatLocalDateTime(LocalDateTime localDateTime) {
    return String.format("%sT%s", BASIC_ISO_DATE.format(localDateTime),
        BASIC_TIME.format(localDateTime));
  }

  public static DateTime toiCalDate(LocalDateTime localDateTime) throws ParseException {
    Preconditions.checkNotNull(localDateTime, "localDateTime cannot be null");

//    return new DateTime(formatLocalDateTime(localDateTime),"",false);
    return new DateTime(formatLocalDateTime(localDateTime));
  }
  public static Date toiCalDate(LocalDate localDate) throws ParseException {
    Preconditions.checkNotNull(localDate, "localDate cannot be null");

    String dateString = localDate.format(DateTimeFormatter.ofPattern("yyyMMdd"));
    return new Date(dateString);
  }
  public static Date toiCalDate(LocalDate localDate, LocalTime localTime) throws ParseException {
    Preconditions.checkNotNull(localDate, "localDate cannot be null");
    Preconditions.checkNotNull(localTime, "localTime cannot be null");

    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    return toiCalDate(localDateTime);
  }

  public static Date toiCalDateTimeOptional(LocalDate localDate, LocalTime localTime) throws
      ParseException {

    Preconditions.checkNotNull(localDate, "localDate cannot be null");

    if (localTime == null) {
        return toiCalDate(localDate);
    } else {
      return toiCalDate(localDate, localTime);
    }
  }


}

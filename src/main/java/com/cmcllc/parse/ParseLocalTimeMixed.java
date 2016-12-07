package com.cmcllc.parse;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.util.CsvContext;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Created by chrismaki on 12/4/16.
 */
public class ParseLocalTimeMixed extends CellProcessorAdaptor {
  private static final DateTimeFormatter twentyFourHourTime = DateTimeFormatter.ofPattern("H:mm");
  private static final DateTimeFormatter amPmTie = DateTimeFormatter.ofPattern("h:mm a");
  private static final String pattern = ".*(AM|PM)";

  public ParseLocalTimeMixed() {
    super();
  }

  @Override
  public Object execute(Object value, CsvContext context) {
    validateInputNotNull(value, context);

    String timeString = value.toString().toUpperCase();

    // decide which format to use...
    DateTimeFormatter formatter = twentyFourHourTime;
    if (Pattern.matches(pattern, timeString)) formatter = amPmTie;

    return LocalTime.parse(timeString, formatter);
  }
}

package com.cmcllc.domain;

import com.cmcllc.util.DateTimeUtils;
import org.apache.commons.lang.StringUtils;

import static com.cmcllc.util.DateTimeUtils.*;
import static java.lang.System.lineSeparator;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

/**
 * Converts Calendar, Event and Alarm objects into .ics format
 * <p>
 * Implementation of File contents for rfc 5545
 * <p>
 * All date-time values for this class will use this definition:
 * https://tools.ietf.org/html/rfc5545#section-3.3.5, specifically FORM #1
 * <p>
 * https://tools.ietf.org/html/rfc5545
 *
 * @author chrismaki
 */
public class CalendarWriter {

  public String writeValueAsString(Calendar calendar) {
    return "";
  }

  public String writeValueAsString(Event event) {
    return "";
  }

  public String writeValueAsString(Alarm alarm) {
    StringBuilder sb = new StringBuilder();
    sb.append("BEGIN:").append(alarm.COMPONENT_NAME).append(lineSeparator());

    if (null != alarm.getTrigger()) {
      sb.append("TRIGGER;VALUE=DATE-TIME:").append(formatLocalDateTime(alarm.getTrigger()))
          .append(lineSeparator());
    }
    // TODO: this may go, it's too complex for what I'm trying to do right now, needs a DURATION
    // setting in addition to the repeat property
    if (alarm.getRepeat() > 0) {
      sb.append("REPEAT:").append(alarm.getRepeat()).append(lineSeparator());
    }
    sb.append("ACTION:").append(alarm.getAction().name()).append(lineSeparator());

    if (StringUtils.isNotBlank(alarm.getDescription())) {
      // TODO: there is a line length limit I believe
      sb.append("DESCRIPTION:").append(alarm.getDescription()).append(lineSeparator());
    }
    if (StringUtils.isNotBlank(alarm.getSummary())) {
      // TODO: there is a line length limit I believe
      sb.append("SUMMARY:").append(alarm.getSummary()).append(lineSeparator());
    }
    sb.append("END:").append(alarm.COMPONENT_NAME).append(lineSeparator());

    return sb.toString();
  }
}

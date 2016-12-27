package com.cmcllc.service;

import com.cmcllc.domain.CalendarEvent;
import com.cmcllc.parse.CalendarEventUtil;
import com.cmcllc.parse.ParseLocalTimeMixed;
import com.cmcllc.parse.ParseStringTrim;
import com.google.common.annotations.VisibleForTesting;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.time.ParseLocalDate;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by chrismaki on 12/4/16.
 */
@Service
public class CalendarParserService {

  private static Logger logger = LoggerFactory.getLogger(CalendarParserService.class);

  private CellProcessor[] default_processors = {
      new Optional(new ParseStringTrim(new Optional(new NotNull()))),             // Subject
      new Optional(new ParseStringTrim(new Optional(new ParseLocalDate(
          DateTimeFormatter.ofPattern("MM/dd/yyy"))))),                           // Start Date
      new Optional(new ParseStringTrim(new Optional(new ParseLocalTimeMixed()))), // Start Time
      new Optional(new ParseStringTrim(new Optional(new ParseLocalDate(
          DateTimeFormatter.ofPattern("MM/dd/yyy"))))),                           // End Date
      new Optional(new ParseStringTrim(new Optional(new ParseLocalTimeMixed()))), // End Time
      new Optional(new ParseStringTrim(new Optional(new ParseBool()))),           // All Day Event
      new Optional(new ParseStringTrim(new Optional(new NotNull()))),             // Description
      new Optional(new ParseStringTrim(new Optional(new NotNull()))),             // Location
      new Optional(new ParseStringTrim(new Optional(new ParseBool()))),            // Private
  };
  private String[] default_headers = {
      "subject", "startDate", "startTime", "endDate", "endTime", "allDayEvent",
      "description", "location", "privateEvent"
  };


  // Calendar Monster addons, support for Alarms
  private CellProcessor[] cm_processors;
  private String[] cm_headers;
  private CellProcessor[] cm_processors_addons = {
      // Additional CSV support unique to calendar-monster
      new Optional(new ParseStringTrim(new Optional(new NotNull()))),           // Alarm Description
      new Optional(new ParseStringTrim(new Optional(new ParseInt()))),          // Alarm Days
      new Optional(new ParseStringTrim(new Optional(new ParseInt()))),          // Alarm Hours
      new Optional(new ParseStringTrim(new Optional(new ParseInt())))           // Alarm Minutes
  };
  private String[] cm_headers_addon = {
      "alarmDescription","alarmDays", "alarmHours",
      "alarmMinutes"
  };

  // My personal format, makes creating the spreadsheet easier
  private CellProcessor[] maki_processors;
  private String[] maki_headers;
  private CellProcessor[] maki_processors_addons = {
      new Optional(),             // Day
      new Optional(),             // Duration
  };
  private String[] maki_headers_addons = {
      "day","duration"
  };


  public CalendarParserService() {
    cm_processors = ArrayUtils.addAll(default_processors, cm_processors_addons);
    cm_headers = ArrayUtils.addAll(default_headers, cm_headers_addon);

    maki_processors = ArrayUtils.addAll(maki_processors_addons, cm_processors);
    maki_headers = ArrayUtils.addAll(maki_headers_addons, cm_headers);
  }

  /**
   * Writes out the ics data to the file provided.
   *
   * @param csvFlie
   * @return path to the File, where we will store the ics data
   * @throws IOException
   */
  public void createCalendarFile(String csvFlie, Path outputFile) throws IOException {
    Calendar cal = createCalendar(csvFlie);


    FileOutputStream output = new FileOutputStream(outputFile.toFile());
    CalendarOutputter outputter = new CalendarOutputter();
    outputter.output(cal, output);
  }

  @VisibleForTesting
  protected String createCalendarStringFromFile(String pathToFile) throws IOException {
    Calendar cal = createCalendar(pathToFile);

    ByteArrayOutputStream output = new ByteArrayOutputStream();
    CalendarOutputter outputter = new CalendarOutputter();
    outputter.output(cal, output);

    return new String(output.toByteArray(), UTF_8);
  }

  private Calendar createCalendar(String pathToFile) throws IOException {
    List<CalendarEvent> calendarEvents = parseCsvFile(pathToFile);
    return createCalendar(calendarEvents);
  }

  public Calendar createCalendar(List<CalendarEvent> events) {
    Calendar calendar = new Calendar();
    calendar.getProperties().add(new ProdId("-//lahdessolutions.com//iCal4j 2.0//EN"));
    calendar.getProperties().add(Version.VERSION_2_0);
    calendar.getProperties().add(CalScale.GREGORIAN);

    for (CalendarEvent event : events) {
      java.util.Optional<VEvent> vEvent = CalendarEventUtil.createVEvent(event);
      if (vEvent.isPresent()) {
        calendar.getComponents().add(vEvent.get());
      }
    }

    return calendar;
  }

  public List<CalendarEvent> parseCsvFile(String pathToFile) throws IOException {
    try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(pathToFile),
        CsvPreference.STANDARD_PREFERENCE)) {

      CalendarEvent calendarEvent;
      String[] csvHeaders = beanReader.getHeader(true);
      String[] headers = default_headers;
      CellProcessor[] processors = default_processors;

      // to account for calendar-monster customizations, there can be two header lengths, one for
      // standard iCal parsing and one for CM parsing
      if (csvHeaders.length == 13) {
        headers = cm_headers;
        processors = cm_processors;
      } else if (csvHeaders.length == 15) {
        headers = maki_headers;
        processors = maki_processors;
      }

      List<CalendarEvent> events = new ArrayList<>();

      while ((calendarEvent = beanReader.read(CalendarEvent.class, headers, processors)) != null) {
        if (calendarEvent.isValid()) events.add(calendarEvent);

        logger.trace("Line={} row={}, event={}", beanReader.getLineNumber(),
            beanReader.getRowNumber(), calendarEvent.getSubject());
      }

      return events;
    }
  }

}

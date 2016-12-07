package com.cmcllc.service;

import com.cmcllc.domain.CalendarEvent;
import com.cmcllc.parse.CalendarEventUtil;
import com.cmcllc.parse.ParseLocalTimeMixed;
import com.cmcllc.parse.ParseStringTrim;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.time.ParseLocalDate;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
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

  private CellProcessor[] processors = {
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
      new Optional(new ParseStringTrim(new Optional(new ParseBool())))            // Private
  };

  private String[] headers = {
      "subject", "startDate", "startTime", "endDate", "endTime", "allDayEvent",
      "description", "location", "privateEvent"
  };

  public String createCalendarFile(String pathToFile) throws IOException {
    List<CalendarEvent> calendarEvents = parseCsvFile(pathToFile);
    Calendar cal = createCalendar(calendarEvents);
    return exportCalendar(cal);
  }

  public String exportCalendar(Calendar calendar) throws IOException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    CalendarOutputter outputter = new CalendarOutputter();
    outputter.output(calendar, output);

    return new String(output.toByteArray(), UTF_8);


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
      // skip the header row, this could cause some issues for user data because I'm not
      // looking at what they are telling me they have, no way to catch errors they may have.
      // TODO: consider parsing actual headers to make sure input is correct
      beanReader.getHeader(true);

      List<CalendarEvent> events = new ArrayList<>();

      while ((calendarEvent = beanReader.read(CalendarEvent.class, headers, processors)) != null) {
        if (calendarEvent.isValid()) events.add(calendarEvent);

        logger.debug("Line={} row={}, event={}", beanReader.getLineNumber(),
            beanReader.getRowNumber(), calendarEvent.getSubject());
      }

      return events;
    }
  }

}

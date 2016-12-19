package com.cmcllc.service;

import com.cmcllc.domain.CalendarEvent;
import com.cmcllc.parse.CalendarEventUtil;
import com.cmcllc.utils.Domains;
import mockit.Mocked;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

/**
 * Created by chrismaki on 12/4/16.
 */
public class CalendarParserServiceTest {

  private static final String OFF_SEASON_CVS = "OffSeason.csv";
  private static final String OFF_SEASON_AM_CVS = "OffSeason-am.csv";
  private static final String TESTER_CSV = "tester.csv";
  private static final String TESTER2_CSV = "tester2.csv";
  private static final String ALARM_CSV = "alarms.csv";
  private String path;
  private String pathAm;
  private String pathTester;
  private String pathTester2;
  private String pathAlarms;

  private CalendarParserService calendarParserService = new CalendarParserService();

  @Before
  public void setup() {
    path = getClass().getClassLoader().getResource(OFF_SEASON_CVS).getPath();
    pathAm = getClass().getClassLoader().getResource(OFF_SEASON_AM_CVS).getPath();
    pathTester = getClass().getClassLoader().getResource(TESTER_CSV).getPath();
    pathTester2 = getClass().getClassLoader().getResource(TESTER2_CSV).getPath();
    pathAlarms = getClass().getClassLoader().getResource(ALARM_CSV).getPath();
  }

  @Test
  public void parseCsvFile() throws Exception {
    calendarParserService.parseCsvFile(path);
  }

  @Test
  public void parseCsvFileAmPm() throws Exception {
    calendarParserService.parseCsvFile(pathAm);
  }

  @Test
  public void parseCsvFileTester() throws Exception {
    calendarParserService.parseCsvFile(pathTester);
  }

  @Test
  public void parseCsvFileTester2() throws Exception {
    calendarParserService.parseCsvFile(pathTester2);
  }

  @Test
  public void testParseAndCreateCalendar() throws IOException {
    String result = calendarParserService.createCalendarStringFromFile(path);

    System.out.println(result);
  }

  @Test
  public void testCreateFile() throws IOException {
    Path path = Files.createTempFile(null, null);
    calendarParserService.createCalendarFile(pathAlarms, path);
    System.out.println(path);

  }
  @Test
  public void createCalendar() throws Exception {
    CalendarEvent event = Domains.newCalendarEvent().setStartDate(LocalDate.of
        (2016, 12, 4)).setStartTime(LocalTime.of(15, 34)).setSubject("the Subject").build();

    Calendar cal = calendarParserService.createCalendar(Arrays.asList(event));
    Component vevent = cal.getComponents().getComponent("VEVENT");

    assertThat(vevent.getProperties().getProperty("DTSTART").getValue(), is("20161204T153400"));
    assertThat(vevent.getProperties().getProperty("SUMMARY").getValue(), is("the Subject"));
  }

  @Test
  public void testParsAlarms() throws IOException {
    String result = calendarParserService.createCalendarStringFromFile(pathAlarms);

    System.out.println(result);
  }
  @Test
  public void createCalendarJustDate() throws Exception {
    CalendarEvent event = Domains.newCalendarEvent().setStartDate(LocalDate.of
        (2016, 12, 4)).setSubject("the Subject").build();

    Calendar cal = calendarParserService.createCalendar(Arrays.asList(event));
    Component vevent = cal.getComponents().getComponent("VEVENT");

    assertThat(vevent.getProperties().getProperty("DTSTART").getValue(), is("20161204"));
    assertThat(vevent.getProperties().getProperty("SUMMARY").getValue(), is("the Subject"));
    assertThat(vevent.getProperties().getProperty("UID").getValue(), notNullValue());

  }


  @Test
  public void testCreateComponent() {
    Optional<VEvent> event = CalendarEventUtil.createVEvent(Domains.newCalendarEvent().setStartDate
        (LocalDate.of(2016, 2, 2)).build());

    // turns out you can create an event without a summary
    assertThat(event, isPresent());
  }

  @Test
  public void testCreateComponent_endDate() {
    Optional<VEvent> event = CalendarEventUtil.createVEvent(Domains.newCalendarEvent().setStartDate
        (LocalDate.of(2016, 2, 2)).setEndDate(LocalDate.of(2016,2,3)).build());

    // turns out you can create an event without a summary
    assertThat(event, isPresent());
  }
  @Test
  public void testCreateComponent_endDateError() {
    Optional<VEvent> event = CalendarEventUtil.createVEvent(Domains.newCalendarEvent().setStartDate
        (LocalDate.of(2016, 2, 2)).setEndDate(LocalDate.of(2016,2,1)).build());

    // turns out you can create an event without and end date before the start....
    assertThat(event, isPresent());
  }

  @Test
  public void testCreateComponent_allDay() throws IOException {
    Optional<VEvent> event = CalendarEventUtil.createVEvent(Domains.newCalendarEvent()
        .setStartDate(LocalDate.of(2016, 2, 2)).setEndDate(LocalDate.of(2016,2,1))
        .setSubject("subject").setAllDayEvent(true).setDescription("description")
        .setLocation("location").setPrivateEvent(true).setAlarmDescription("alarm description")
        .setAlarmMinutes(5).build());

    // turns out you can create an event without and end date before the start....
    assertThat(event, isPresent());

    String result = printEvent(event.get());

    assertThat(result, containsString("DTSTART;VALUE=DATE:20160202"));
    assertThat(result, containsString("DTSTART;VALUE=DATE:20160202"));
    assertThat(result, containsString("SUMMARY:"));
    assertThat(result, containsString("TRANSPARENT"));
    assertThat(result, containsString("location"));
    assertThat(result, containsString("description"));
    assertThat(result, containsString("PRIVATE"));
    assertThat(result, containsString("subject"));
    assertThat(result, containsString("VALARM"));
    assertThat(result, containsString("DISPLAY"));
    assertThat(result, containsString("TRIGGER:-PT5M"));
  }

  @Test
  public void testCreateComponent_badEvent() {
    Optional<VEvent> event = CalendarEventUtil.createVEvent(Domains.newCalendarEvent().build());

    // turns out you can create an event without a summary
    assertThat(event, isEmpty());
  }

  private String printEvent(VEvent event) throws IOException {
    Calendar calendar = new Calendar();
    calendar.getProperties().add(new ProdId("-//lahdessolutions.com//iCal4j 2.0//EN"));
    calendar.getProperties().add(Version.VERSION_2_0);

//    // add a UID to the event, this puts in an empty UID
//    event.getProperties().add(new Uid(event.getUid().toString()));

    calendar.getComponents().add(event);

    ByteArrayOutputStream output = new ByteArrayOutputStream();
    CalendarOutputter outputter = new CalendarOutputter();
    outputter.output(calendar, output);

    return new String(output.toByteArray(), UTF_8);

  }
}
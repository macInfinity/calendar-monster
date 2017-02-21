package com.cmcllc.parse;

import com.cmcllc.domain.CalendarEvent;
import net.fortuna.ical4j.model.component.VEvent;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.junit.Assert.assertThat;

/**
 * Test to work with the calendar parser. In particular, generate an error when Start Date and
 * end Date are transposed.
 *
 */
public class CalendarEventUtilTest {
  private CalendarEvent calendarEvent;
  private LocalDate today;
  private LocalTime startTime;
  private LocalTime endTime;


  @Before
  public void setUp() throws Exception {
    today = LocalDate.parse("2017-02-20");
    startTime = LocalTime.parse("13:30:00");
    endTime = startTime.plusMinutes(30); // end date is before start

    calendarEvent = new CalendarEvent();
    calendarEvent.setDescription("test description");
    calendarEvent.setStartDate(today);
    calendarEvent.setStartTime(startTime);

    calendarEvent.setEndDate(today);
    calendarEvent.setEndTime(endTime);

  }

  @After
  public void tearDown() throws Exception {
    calendarEvent = null;
  }

  /**
   * https://github.com/macInfinity/calendar-monster/issues/14
   */
  @Test
  public void createVEvent_time() throws Exception {
    // time is before start
    calendarEvent.setEndTime(startTime.minusMinutes(30));

    Optional<VEvent> result = CalendarEventUtil.createVEvent(calendarEvent);

    assertThat(result, isPresent());
    // end date/time should match start
    //noinspection OptionalGetWithoutIsPresent
    assertThat(result.get().getProperties().getProperty("DTEND").getValue(),
        Matchers.containsString("20170220T133000"));
  }

  /**
   * https://github.com/macInfinity/calendar-monster/issues/14
   */
  @Test
  public void createVEvent_date() throws Exception {
    // date of event is before start
    calendarEvent.setEndDate(today.minusDays(1));

    Optional<VEvent> result = CalendarEventUtil.createVEvent(calendarEvent);

    assertThat(result, isPresent());
    // end date/time should match start
    //noinspection OptionalGetWithoutIsPresent
    assertThat(result.get().getProperties().getProperty("DTEND").getValue(),
        Matchers.containsString("20170220T133000"));
  }

  /**
   * https://github.com/macInfinity/calendar-monster/issues/14
   */
  @Test
  public void createVEvent_dateTime() throws Exception {
    // date AND time are before start
    calendarEvent.setEndDate(today.minusDays(1));
    calendarEvent.setEndTime(startTime.minusMinutes(30));

    Optional<VEvent> result = CalendarEventUtil.createVEvent(calendarEvent);

    assertThat(result, isPresent());
    // end date/time should match start
    //noinspection OptionalGetWithoutIsPresent
    assertThat(result.get().getProperties().getProperty("DTEND").getValue(),
        Matchers.containsString("20170220T133000"));
  }

  @Test
  public void createVEvent() throws Exception {
    // event end is 24 hours and 30 minutes after start
    calendarEvent.setEndDate(today.plusDays(1));
    Optional<VEvent> result = CalendarEventUtil.createVEvent(calendarEvent);

    assertThat(result, isPresent());
    // end date/time should match start
    //noinspection OptionalGetWithoutIsPresent
    assertThat(result.get().getProperties().getProperty("DTEND").getValue(),
        Matchers.containsString("20170221T140000"));
  }

}
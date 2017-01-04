package com.cmcllc.util;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by chrismaki on 12/4/16.
 */
public class DateTimeUtilsTest {
  LocalDate date;
  LocalTime time;
  LocalDateTime localDateTime;
  SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

  @Before
  public void setup() {
    date = LocalDate.of(2016, 12, 4);
    time = LocalTime.of(13, 15);
    localDateTime = LocalDateTime.of(date, time);
  }

  @Test
  public void toiCalDate() throws Exception {

    net.fortuna.ical4j.model.Date iCalDate = DateTimeUtils.toiCalDate(date);

    assertThat(iCalDate.toString(), is("20161204"));
  }

  @Test
  public void toiCalDateTime() throws Exception {
    net.fortuna.ical4j.model.Date iCalDate = DateTimeUtils.toiCalDate(date, time);
    // TODO: Travis CI has Zone info added to the time, not sure why
    assertThat(dateTimeFormat.format(iCalDate), is ("20161204T131500"));
  }

  @Test
  public void toiCalDateTime2() throws Exception {
    net.fortuna.ical4j.model.Date iCalDate = DateTimeUtils.toiCalDate(localDateTime);
    assertThat(dateTimeFormat.format(iCalDate), is ("20161204T131500"));
  }

  @Test
  public void testZeroHour_success() throws ParseException {
    LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2016, 12, 4),
        LocalTime.of(13, 15, 0,0));
    DateTimeUtils.toiCalDate(dateTime);
  }
  @Test
  public void testZeroHour_error() throws ParseException {
    LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2016, 12, 4),
        LocalTime.of(13, 1, 0,0));
    DateTimeUtils.toiCalDate(dateTime);
  }

  @Test
  public void testZeroHour_sweetSpot() throws Exception {
    // looking for exact spot where old time format would fail
    // this test used 'i' for hour and minute, when either was a single digit
    // it used to fail
    boolean failure = false;
    for (int i = 0; i < 24; i++) {
      try {
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2016, 12, 4),
            LocalTime.of(i, i, 0,0));
        DateTimeUtils.toiCalDate(dateTime);
      } catch (Throwable t) {
        System.out.println(t);
        failure = true;
      }
    }
    if (failure) throw new Exception("error during test");
  }

  @Test
  public void testZeroHour_sweetSpot2() throws Exception {
    // looking for exact spot where old time format would fail
    // this test used 'i' minute ONLY, when minute a single digit
    // it used to fail
    boolean failure = false;
    for (int i = 0; i < 24; i++) {
      try {
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2016, 12, 4),
            LocalTime.of(13, i, 0,0));
        DateTimeUtils.toiCalDate(dateTime);
      } catch (Throwable t) {
        failure = true;
        System.out.println(t);
      }
    }
    if (failure) throw new Exception("error during test");
  }

  }
package com.cmcllc.util;

import org.junit.Before;
import org.junit.Test;

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
    assertThat(iCalDate.toString(), is ("20161204T131500"));
  }

  @Test
  public void toiCalDateTime2() throws Exception {
    net.fortuna.ical4j.model.Date iCalDate = DateTimeUtils.toiCalDate(localDateTime);
    assertThat(iCalDate.toString(), is ("20161204T131500"));
  }

//
//    LocalTime localTime = LocalTime.of(13, 15);
//    Date converted = DateTimeUtils.toiCalDate(localTime);
//
//    Calendar cal = Calendar.getInstance();
//    cal.setTime(converted);
//
//    assertThat(cal.get(Calendar.HOUR_OF_DAY), is(13));
//    assertThat(cal.get(Calendar.MINUTE), is(15)); // Jan = 0
//  }

}
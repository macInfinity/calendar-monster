package com.cmcllc.domain;

import com.cmcllc.utils.DataCreator;
import com.cmcllc.utils.Domains;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.cmcllc.domain.Alarm.AlarmAction.DISPLAY;
import static com.cmcllc.utils.Domains.newAlarm;
import static com.cmcllc.utils.Domains.newEvent;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by chrismaki on 11/27/16.
 */
public class CalendarTest {

  private DataCreator dataCreator = new DataCreator();

  private Calendar calendar;
  private Alarm alarm;
  private Event event;


  @Before
  /**
   * Be design, the Calendar, Event and Alarm fields are not connected. You need to connect
   * the objects as you see fit in your test method.
   */
  public void setUp() throws Exception {
    dataCreator.setup();
    calendar = dataCreator.getCalendar();
    event = dataCreator.getEvent();
    alarm = dataCreator.getAlarm();
  }

  @Test
  public void testCreateCalendar() {
    calendar.addEvent(event);
    // make sure we are connecting object correctly
    assertThat(calendar.getEvents().get(0), is(event));

    event.addAlarm(alarm);
    assertThat(event.getAlarms().get(0), is(alarm));
  }

}
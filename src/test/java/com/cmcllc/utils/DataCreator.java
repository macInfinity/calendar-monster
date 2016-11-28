package com.cmcllc.utils;

import com.cmcllc.domain.Alarm;
import com.cmcllc.domain.Calendar;
import com.cmcllc.domain.Event;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.cmcllc.domain.Alarm.AlarmAction.DISPLAY;
import static com.cmcllc.utils.Domains.newAlarm;
import static com.cmcllc.utils.Domains.newEvent;

/**
 * Created by chrismaki on 11/27/16.
 */
public class DataCreator {

  private Calendar calendar;
  private LocalDateTime start;
  private LocalDateTime end;
  private Alarm alarm;
  private Event event;

  public void setup() {
    calendar = new Calendar();

    start = LocalDateTime.parse("2016-04-02T13:00:00");
    LocalDateTime dateTimeAlarm = start.minusMinutes(15);
    end = start.plusMinutes(90);

    alarm = newAlarm().setAction(DISPLAY).setTrigger(dateTimeAlarm).setDuration(
        Duration.ofSeconds(90)).setDescription("Alarm description").build();

    event = newEvent().setAllDayEvent(false).setComment("this is a comment")
        .setDateTimeStart(start).setDateTimeEnd(end).setLocation("Home").setPriority(3)
        .setDescription("Event description").build();
  }

  public Calendar getCalendar() {
    return calendar;
  }

  public void setCalendar(Calendar calendar) {
    this.calendar = calendar;
  }

  public LocalDateTime getStart() {
    return start;
  }

  public void setStart(LocalDateTime start) {
    this.start = start;
  }

  public LocalDateTime getEnd() {
    return end;
  }

  public void setEnd(LocalDateTime end) {
    this.end = end;
  }

  public Alarm getAlarm() {
    return alarm;
  }

  public void setAlarm(Alarm alarm) {
    this.alarm = alarm;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }
}

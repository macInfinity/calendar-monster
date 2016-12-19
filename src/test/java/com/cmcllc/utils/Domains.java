package com.cmcllc.utils;

import com.cmcllc.domain.CalendarEvent;
import mockit.Deencapsulation;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chrismaki on 11/27/16.
 */
public class Domains {
  public static CalendarEventBuilder newCalendarEvent() {
    return new CalendarEventBuilder();
  }

  public static class CalendarEventBuilder extends Builder<CalendarEvent, CalendarEventBuilder> {

    public CalendarEventBuilder setSubject(String subject) {
      return setField("subject", subject);
    }
    public CalendarEventBuilder setStartDate(LocalDate startDate) {
      return setField("startDate", startDate);
    }
    public CalendarEventBuilder setStartTime(LocalTime startTime) {
      return setField("startTime", startTime);
    }
    public CalendarEventBuilder setEndDate(LocalDate endDate) {
      return setField("endDate", endDate);
    }
    public CalendarEventBuilder setEndTime(LocalTime endTime) {
      return setField("endTime", endTime);
    }
    @Override
    protected CalendarEvent newInstance() {
      return new CalendarEvent();
    }

    public CalendarEventBuilder setAllDayEvent(boolean allDayEvent) {
      return setField("allDayEvent", allDayEvent);
    }

    public CalendarEventBuilder setLocation(String location) {
      return setField("location", location);
    }
    public CalendarEventBuilder setPrivateEvent(boolean privateEvent) {
      return setField("privateEvent", privateEvent);
    }
    public CalendarEventBuilder setAlarmDescription(String alarmDescription) {
      return setField("alarmDescription", alarmDescription);
    }
    public CalendarEventBuilder setAlarmDays(int alarmDays) {
      return setField("alarmDays", alarmDays);
    }
    public CalendarEventBuilder setAlarmHours(int alarmHours) {
      return setField("alarmHours", alarmHours);
    }
    public CalendarEventBuilder setAlarmMinutes(int alarmMinutes) {
      return setField("alarmMinutes", alarmMinutes);
    }
  }

  /**
   * Superclass for all "builders"
   *
   * @param <T> The model object you want a builder for
   * @param <B> The builder object you are creating
   */
  public static abstract class Builder<T, B extends Builder<T, B>> {

    private final Map<String, Object> values = new HashMap<>();

    public B setSummary(String summary) {
      return setField("summary", summary);
    }

    public B setDescription(String description) {
      return setField("description", description);
    }

    protected abstract T newInstance();

    public B setField(String field, Object value) {
      values.put(field, value);
      return (B) this;
    }

    public T build() {
      T obj = newInstance();
      for (String key : values.keySet()) {
        Deencapsulation.setField(obj, key, values.get(key));
      }
      return obj;
    }
  }
}

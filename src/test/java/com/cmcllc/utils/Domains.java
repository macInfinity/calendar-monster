package com.cmcllc.utils;

import com.cmcllc.domain.Alarm;
import com.cmcllc.domain.Event;
import mockit.Deencapsulation;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chrismaki on 11/27/16.
 */
public class Domains {

  public static EventBuilder newEvent() { return new EventBuilder(); }
  
  public static class EventBuilder extends Builder<Event, EventBuilder> {

    public EventBuilder setDateTimeStamp(LocalDateTime dateTimeStamp) {
      return setField("dateTimeStamp", dateTimeStamp);
    }
    
    public EventBuilder setDateTimeStart(LocalDateTime dateTimeStart) {
      return setField("dateTimeStart", dateTimeStart);
    }
    
    public EventBuilder setDateTimeEnd(LocalDateTime dateTimeEnd) {
      return setField("dateTimeEnd", dateTimeEnd);
    }
    
    public EventBuilder setLocation(String location) {
      return setField("location", location);
    }
    
    public EventBuilder setPriority(int priority) {
      return setField("priority", priority);
    }
    
    public EventBuilder setComment(String comment) {
      return setField("comment", comment);
    }
    
    public EventBuilder setAllDayEvent(boolean allDayEvent) {
      return setField("allDayEvent", allDayEvent);
    }
    
    @Override
    protected Event newInstance() {
      return new Event();
    }
  }
  public static AlarmBuilder newAlarm() {
    return new AlarmBuilder();
  }

  public static class AlarmBuilder extends Builder<Alarm, AlarmBuilder> {

    public AlarmBuilder setDuration(Duration duration) {
      return setField("duration", duration);
    }
    
    public AlarmBuilder setRepeat(int repeat) {
      return setField("repeat", repeat);
    }
    
    public AlarmBuilder setUrl(URL url) {
      return setField("url", url);
    }
    
    
    public AlarmBuilder setAction(Alarm.AlarmAction action) {
      return setField("action", action);
    }

    public AlarmBuilder setTrigger(String trigger) {
      return setField("trigger", trigger);
    }

    @Override
    protected Alarm newInstance() {
      return new Alarm();
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

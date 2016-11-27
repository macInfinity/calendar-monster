package com.cmcllc.domain;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jersey.repackaged.com.google.common.base.Preconditions.checkNotNull;

/**
 * Representation of the Event Component from rfc 5545, section 3.6.1 Event Component
 * https://tools.ietf.org/html/rfc5545
 *
 * @author chrismaki
 */
@AutoProperty
public class Event extends CalendarBase {

  public static String COMPONENT_NAME = "VEVENT"; // see section 3.6.1

  /**
   * This property specifies the date and time that the information
   * associated with the calendar component was last revised in the
   * calendar store.
   *
   * REQUIRED property
   *
   * https://tools.ietf.org/html/rfc5545#section-3.8.7.2
   */
  private LocalDateTime dateTimeStamp = LocalDateTime.now();

  /**
   * This property specifies when the calendar component begins.
   *
   * OPTIONAL property
   *
   * https://tools.ietf.org/html/rfc5545#section-3.8.2.4
   */
  private LocalDateTime dateTimeStart;

  /**
   * This property defines the intended venue for the activity
   * defined by a calendar component
   *
   * OPTIONAL property
   *
   * https://tools.ietf.org/html/rfc5545#section-3.8.1.9
   */
  private String location;

  /**
   * This priority is specified as an integer in the range 0
   * to 9.  A value of 0 specifies an undefined priority.  A value of 1
   * is the highest priority.  A value of 2 is the second highest
   * priority.  Subsequent numbers specify a decreasing ordinal
   * priority.  A value of 9 is the lowest priority.
   *
   * OPTIONAL property
   *
   * https://tools.ietf.org/html/rfc5545#section-3.8.1.9
   */
  private int priority;

  /**
   * This property specifies non-processing information intended
   * to provide a comment to the calendar user.
   *
   * OPTIONAL property
   *
   * https://tools.ietf.org/html/rfc5545#section-3.8.1.4
   */
  private String comment;

  /**
   * This property specifies the date and time that a calendar
   * component ends.
   *
   * MAY appear but ONLY one MAY in the same event.
   *
   * https://tools.ietf.org/html/rfc5545#section-3.8.2.2
   */
  private LocalDateTime dateTimeEnd;

  /**
   * Internal property, set to indicate this is an all-day event
   */
  private boolean allDayEvent = false;

  /**
   * Alarms associated with this event, typically one but there can be many.
   */
  private List<Alarm> alarms = new ArrayList<>();

  public void addAlarm(Alarm alarm) {
    checkNotNull(alarm);

    alarms.add(alarm);
  }

  public LocalDateTime getDateTimeStamp() {
    return dateTimeStamp;
  }

  public void setDateTimeStamp(LocalDateTime dateTimeStamp) {
    this.dateTimeStamp = dateTimeStamp;
  }

  public LocalDateTime getDateTimeStart() {
    return dateTimeStart;
  }

  public void setDateTimeStart(LocalDateTime dateTimeStart) {
    this.dateTimeStart = dateTimeStart;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public LocalDateTime getDateTimeEnd() {
    return dateTimeEnd;
  }

  public void setDateTimeEnd(LocalDateTime dateTimeEnd) {
    this.dateTimeEnd = dateTimeEnd;
  }

  public boolean isAllDayEvent() {
    return allDayEvent;
  }

  public void setAllDayEvent(boolean allDayEvent) {
    this.allDayEvent = allDayEvent;
  }

  public List<Alarm> getAlarms() {
    return alarms;
  }

  public void setAlarms(List<Alarm> alarms) {
    this.alarms = alarms;
  }

  @Override
  public int hashCode() {
    return Pojomatic.hashCode(this);
  }

  @Override
  public boolean equals(Object other) {
    return Pojomatic.equals(this, other);
  }

  @Override
  public String toString() {
    return Pojomatic.toString(this);
  }


}

package com.cmcllc.domain;

import jersey.repackaged.com.google.common.base.Preconditions;
import org.apache.commons.validator.routines.EmailValidator;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Representation of the Alarm Component from rfc 5545, section 3.6.6 Alarm Component
 * https://tools.ietf.org/html/rfc5545
 *
 * VEVENTS MUST be appear within either a VEVENT or VTODO. VEVENTS cannot be nested.
 *
 * @author chrismaki
 */
@AutoProperty
public class Alarm extends CalendarBase {
  public enum AlarmAction { AUDIO, DISPLAY, EMAIL}

  public static String COMPONENT_NAME = "VALARM"; // see section 3.6.6

  // REQUIRED properties - all types
  /**
   * This property defines the action to be invoked when an
   * alarm is triggered.
   *
   * https://tools.ietf.org/html/rfc5545#section-3.8.6.1
   */
  private AlarmAction action;
  /**
   * This property specifies when an alarm will trigger.
   *
   * The default value type is DURATION.
   *
   * see sepc, there's a lot of variation here.
   *
   * https://tools.ietf.org/html/rfc5545#section-3.8.6.3
   */
  private String trigger;

  /**
   * This value type is used to identify properties that contain
   * a duration of time.
   *
   * There is a lot here, see specification for details.
   *
   * https://tools.ietf.org/html/rfc5545#section-3.3.6
   */
  private Duration duration;

  /**
   * This property defines the number of times the alarm should
   * be repeated, after the initial trigger.
   *
   * Used with duration to indication how many times this alary will be repeated.
   * duration indicates time between alarms.
   *
   * https://tools.ietf.org/html/rfc5545#section-3.8.6.2
   *
   * @see #duration
   */
  private int repeat = 0;

  /**
   * This property provides the capability to associate a
   * document object with a calendar component.
   *
   * Used to attached an audio sound to be played with alarm
   *
   * https://tools.ietf.org/html/rfc5545#section-3.8.1.1
   */
  private URL attachment; // see section 3.8.1.1

  /**
   * This property defines an "Attendee" within a calendar
   * component.
   *
   * This property MUST only be specified within calendar
   * components to specify participants, non-participants, and the
   * chair of a group-scheduled calendar entity.  The property is
   * specified within an "EMAIL" category of the "VALARM" calendar
   * component to specify an email address that is to receive the email
   * type of iCalendar alarm.
   *
   * https://tools.ietf.org/html/rfc5545#section-3.8.4.1
   */
  private List<String> attendees = new ArrayList<>();

  public AlarmAction getAction() {
    return action;
  }

  public void setAction(AlarmAction action) {
    this.action = action;
  }

  public String getTrigger() {
    return trigger;
  }

  public void setTrigger(String trigger) {
    this.trigger = trigger;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public int getRepeat() {
    return repeat;
  }

  public void setRepeat(int repeat) {
    this.repeat = repeat;
  }

  public URL getAttachment() {
    return attachment;
  }

  public void setAttachment(URL attachment) {
    this.attachment = attachment;
  }

  public void addAttendee(String email) {
    Preconditions.checkArgument(EmailValidator.getInstance().isValid(email),
        "invalid email address");

    attendees.add(email);
  }

  public List<String> getAttendees() {
    return attendees;
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

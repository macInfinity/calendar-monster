package com.cmcllc.domain;

import jersey.repackaged.com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of the iCalendar object from rfc 5545, section 3.4 iCalendar Object.
 * https://tools.ietf.org/html/rfc5545
 *
 * @author chrismaki
 */
public class Calendar {

  public static final String COMPONENT_NAME = "VCALENDAR";

  /**
   * This property specifies the identifier for the product that
   * created the iCalendar object.
   *
   * REQUIRED property, specified once
   *
   *
   * https://tools.ietf.org/html/rfc5545#section-3.7.3
   */
  private String prodid = "lahdessolutions.com"; // default to our site

  /**
   * This property specifies the identifier corresponding to the
   * highest version number or the minimum and maximum range of the
   * iCalendar specification that is required in order to interpret the
   * iCalendar object.
   *
   * REQUIRED property
   *
   * A value of "2.0" corresponds to this memo.
   *
   * https://tools.ietf.org/html/rfc5545#section-3.7.4
   */
  private String version = "2.0"; // default, current version

  private List<Event> events = new ArrayList<>();

  public void addEvent(Event event) {
    Preconditions.checkNotNull(event);

    events.add(event);
  }

  public String getProdid() {
    return prodid;
  }

  public String getVersion() {
    return version;
  }

  public List<Event> getEvents() {
    return events;
  }
}

package com.cmcllc.domain;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.util.UUID;

/**
 * Implementation of the iCalendar object from rfc 5545
 * https://tools.ietf.org/html/rfc5545
 *
 * Definition of all Components can be found here:
 * https://tools.ietf.org/html/rfc5545#section-8.3.1
 *
 * Definition of all Properties can be found here:
 * https://tools.ietf.org/html/rfc5545#section-8.3.2
 *
 * @author chrismaki
 */
@AutoProperty
public class CalendarBase {
  // every Event will have it's own UUID
  /**
   * This property defines the persistent, globally unique
   * identifier for the calendar component.
   *
   * REQUIRED property
   *
   * https://tools.ietf.org/html/rfc5545#section-3.8.4.7
   */
  private String uid = UUID.randomUUID().toString();

  /**
   * This property provides a more complete description of the
   * calendar component than that provided by the "SUMMARY" property.
   *
   * VEVENT - Capture lengthy textual description of the activity
   * VALARM - Display text of alarm
   */
  private String description; // see section 3.8.1.5
  /**
   * This property defines a short summary or subject for the
   * calendar component.
   *
   * OPTIONAL property
   *
   * https://tools.ietf.org/html/rfc5545#section-3.8.1.12
   */
  private String summary;

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

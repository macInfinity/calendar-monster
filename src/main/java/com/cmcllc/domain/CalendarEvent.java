package com.cmcllc.domain;

import org.apache.commons.lang3.StringUtils;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Created by chrismaki on 12/4/16.
 */
@AutoProperty
public class CalendarEvent {
  private String subject;
  private LocalDate startDate;
  private LocalTime startTime;
  private LocalDate endDate;
  private LocalTime endTime;
  private Boolean allDayEvent = false;
  private String description;
  private String location;
  private Boolean privateEvent = false;
  private UUID uuid = UUID.randomUUID();

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  public Boolean isAllDayEvent() {
    return allDayEvent;
  }

  public void setAllDayEvent(Boolean allDayEvent) {
    this.allDayEvent = allDayEvent;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Boolean isPrivateEvent() {
    return privateEvent;
  }

  public void setPrivateEvent(Boolean privateEvent) {
    this.privateEvent = privateEvent;
  }

  public UUID getUuid() {
    return uuid;
  }

  public boolean isValid() {
    // TODO: this will need a lot more becuase there are multiple situations where partial data
    // doesn't make sense
    return StringUtils.isNotBlank(getSubject()) && (getStartDate() != null);
  }
  @Override
  public int hashCode() {
    return Pojomatic.hashCode(this);
  }

  @Override
  public boolean equals(Object obj) {
    return Pojomatic.equals(this, obj);
  }

  @Override
  public String toString() {
    return Pojomatic.toString(this);
  }

}

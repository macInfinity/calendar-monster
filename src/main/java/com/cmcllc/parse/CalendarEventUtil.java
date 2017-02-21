package com.cmcllc.parse;

import com.cmcllc.domain.CalendarEvent;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Normalizer;
import java.util.Optional;

import static com.cmcllc.util.DateTimeUtils.toiCalDate;
import static com.cmcllc.util.DateTimeUtils.toiCalDateTimeOptional;
import static java.text.Normalizer.normalize;

/**
 * Created by chrismaki on 12/5/16.
 *
 * Utility to convert custom Calendar Event objects into VEvent objects.
 */
public class CalendarEventUtil {

  private static final Logger logger = LoggerFactory.getLogger(CalendarEventUtil.class);

  private CalendarEventUtil() {
  }

  /**
   * Creates a VEvent from a CalendarEvent, if a VEvent cannot be created, an empty Optional is
   * returned.
   * @param event the CalendarEvent to be turned into a VEvent
   * @return a VEvent if successfully created or an empty Optional if it cannot be created
   */
  public static Optional<VEvent> createVEvent(CalendarEvent event) {
    VEvent vEvent = null;
    try {

      // by default, non-all day events will be opaque
      Transp transp = Transp.OPAQUE;

      if (event.isAllDayEvent()) {
        vEvent = new VEvent(toiCalDate(event.getStartDate()), event.getSubject());
        // setting all day to transparent
        transp = Transp.TRANSPARENT;
      } else if (event.getEndDate() == null) {
        vEvent = new VEvent(toiCalDateTimeOptional(event.getStartDate(), event.getStartTime()),
            event.getSubject());
      } else {
        if (event.getEndDate().isBefore(event.getStartDate()) ||
            event.getEndTime().isBefore(event.getStartTime())) {
          // https://github.com/macInfinity/calendar-monster/issues/14
          logger.warn("Event start {} is after end {}, defaulting to same date",
              event.getStartDate(), event.getEndDate());
          // set the end date/time to the start time.
          event.setEndTime(event.getStartTime());
          event.setEndDate(event.getStartDate());
        }
        vEvent = new VEvent(toiCalDateTimeOptional(event.getStartDate(), event.getStartTime()),
            toiCalDateTimeOptional(event.getEndDate(), event.getEndTime()), event.getSubject());
      }

      vEvent.getProperties().add(transp);
      vEvent.getProperties().add(new Uid(event.getUuid().toString()));

      if (StringUtils.isNotBlank(event.getDescription())) {
        // FIX: https://github.com/macInfinity/calendar-monster/issues/4
        String normalizedDescription = normalize(event.getDescription(), Normalizer.Form.NFC);
        vEvent.getProperties().add(new Description(normalizedDescription));
      }
      if (StringUtils.isNotBlank(event.getLocation())) {
        String normalizedLocation = normalize(event.getLocation(), Normalizer.Form.NFC);
        vEvent.getProperties().add(new Location(normalizedLocation));
      }
      // only mark private if set as private
      if (event.isPrivateEvent()) {
        vEvent.getProperties().add(Clazz.PRIVATE);
      }

      // only add an alarm if it's defined
      if (StringUtils.isNoneBlank(event.getAlarmDescription()) &&
          (event.getAlarmDays() + event.getAlarmHours() + event.getAlarmMinutes() > 0)) {

        Dur duratoin = new Dur(event.getAlarmDays(), event.getAlarmHours(),
            event.getAlarmMinutes(), 0).negate();
        VAlarm alarm = new VAlarm(duratoin);
        alarm.getProperties().add(Action.DISPLAY);
        String normalizedAlarmDesc = normalize(event.getAlarmDescription(), Normalizer.Form.NFC);
        alarm.getProperties().add(new Description(normalizedAlarmDesc));
        vEvent.getAlarms().add(alarm);

      }

    } catch (Exception e) {
      logger.warn("error creating a VEvent, message: {}", e.getMessage());
    }

    return Optional.ofNullable(vEvent);
  }

}

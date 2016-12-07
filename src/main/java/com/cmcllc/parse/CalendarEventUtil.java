package com.cmcllc.parse;

import com.cmcllc.domain.CalendarEvent;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

import static com.cmcllc.util.DateTimeUtils.toiCalDate;
import static com.cmcllc.util.DateTimeUtils.toiCalDateTimeOptional;

/**
 * Created by chrismaki on 12/5/16.
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
      } else{
        vEvent = new VEvent(toiCalDateTimeOptional(event.getStartDate(), event.getStartTime()),
            toiCalDateTimeOptional(event.getEndDate(), event.getEndTime()), event.getSubject());
      }

      vEvent.getProperties().add(transp);
      vEvent.getProperties().add(new Uid(event.getUuid().toString()));

      if (StringUtils.isNotBlank(event.getDescription())) {
        vEvent.getProperties().add(new Description(event.getDescription()));
      }
      if (StringUtils.isNotBlank(event.getLocation())) {
        vEvent.getProperties().add(new Location(event.getLocation()));
      }
      // only mark private if set as private
      if (event.isPrivateEvent()) {
        vEvent.getProperties().add(Clazz.PRIVATE);
      }

    } catch (Exception e) {
      logger.warn("error creating a VEvent, message: {}", e.getMessage());
    }

    return Optional.ofNullable(vEvent);
  }

}

package com.cmcllc.domain;

import com.cmcllc.util.DateTimeUtils;
import com.cmcllc.utils.DataCreator;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

import static com.cmcllc.util.DateTimeUtils.toiCalDateTime;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by chrismaki on 11/27/16.
 */
public class AlarmTest {

  private Alarm alarm;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() {
    alarm = new Alarm();
  }

  @Test
  public void addAttendee() throws Exception {
    String validEmail = "a@b.com";
    alarm.addAttendee(validEmail);

    // http://blog.codeleak.pl/2014/03/junit-expectedexception-rule-beyond.html
    thrown.expect(IllegalArgumentException.class);
    String invalidEmail = "foo.xx";
    alarm.addAttendee(invalidEmail);
  }
  @Test
  public void testNewLibrary() throws IOException {
    DataCreator creator = new DataCreator();
    creator.setup();

    net.fortuna.ical4j.model.Calendar calendar = new Calendar();
    calendar.getProperties().add(new ProdId("-//lahdessolutions.com//iCal4j 2.0//EN"));
    calendar.getProperties().add(Version.VERSION_2_0);
    calendar.getProperties().add(CalScale.GREGORIAN);

    UidGenerator ug = new UidGenerator("1");
    VEvent event = new VEvent(toiCalDateTime(creator.getStart()),toiCalDateTime(creator.getEnd()),
        "Summary");
    event.getProperties().add(ug.generateUid());

    // 15 minutes before the event
    Dur duration = new Dur(0, 0, -15, 0);
    VAlarm alarm = new VAlarm(duration);
    alarm.getProperties().add(new Summary("This is the summary for an alarm"));
    alarm.getProperties().add(new Description("the description of the alarm"));
    event.getAlarms().add(alarm);
    calendar.getComponents().add(event);


    ByteArrayOutputStream output = new ByteArrayOutputStream();
    CalendarOutputter outputter = new CalendarOutputter();
    outputter.output(calendar, output);
    System.out.println(new String(output.toByteArray(), UTF_8));

  }

}
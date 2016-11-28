package com.cmcllc.domain;

import com.cmcllc.utils.DataCreator;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;

import static org.junit.Assert.*;

/**
 * Created by chrismaki on 11/27/16.
 */
public class CalendarWriterTest {

  DataCreator dataCreator = new DataCreator();
  CalendarWriter calendarWriter = new CalendarWriter();

  @Before
  public void setup() {
    dataCreator.setup();
  }

  @Test
  public void writeValueAsStringCalendar() throws Exception {

  }

  @Test
  public void writeValueAsStringEvent() throws Exception {

  }

  @Test
  public void writeValueAsStringAlarm() throws Exception {
    String result = calendarWriter.writeValueAsString(dataCreator.getAlarm());

    generalValidationAlarm(result);
    System.out.println(result);
  }


  @Test
  public void writeValueAsStringAlarm_moreFields() throws Exception {
    String summary = "*** this is a summary **";
    dataCreator.getAlarm().setSummary(summary);

    String result = calendarWriter.writeValueAsString(dataCreator.getAlarm());

    generalValidationAlarm(result);
    Assert.assertThat(result, StringContains.containsString(summary));

    System.out.println(result);
  }


  private void generalValidationAlarm(String result) {
    Assert.assertThat(result, StringContains.containsString("BEGIN:VALARM"));
    Assert.assertThat(result, StringContains.containsString("TRIGGER;VALUE=DATE-TIME:"));
    Assert.assertThat(result, StringContains.containsString("END:VALARM"));
  }

}
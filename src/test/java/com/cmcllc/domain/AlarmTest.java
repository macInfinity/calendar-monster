package com.cmcllc.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

}
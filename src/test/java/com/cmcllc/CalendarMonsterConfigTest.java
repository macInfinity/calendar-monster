package com.cmcllc;

import mockit.Deencapsulation;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by chrismaki on 1/8/17.
 */
public class CalendarMonsterConfigTest {
  private CalendarMonsterConfig config;

  @Before
  public void setup() {
    config = new CalendarMonsterConfig();
  }

  @Test
  public void getShortAppSha1_noValue()  {
    Deencapsulation.setField(config, "appSha1", null);
    String result = config.getShortAppSha1();
    assertThat(result, is(""));
  }

  @Test
  public void getShortAppSha1_jacked()  {
    Deencapsulation.setField(config, "appSha1", "1234");
    String result = config.getShortAppSha1();
    assertThat(result, is(""));
  }

  @Test
  public void getShortAppSha1()  {
    Deencapsulation.setField(config, "appSha1", "506af8698a5162d380192c9e722a721c064d7d37w");
    String result = config.getShortAppSha1();
    assertThat(result, is("506af8"));
  }

}
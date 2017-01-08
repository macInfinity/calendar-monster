package com.cmcllc;

import mockit.Deencapsulation;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by chrismaki on 1/8/17.
 */
public class CalendarMonsterConfigTest {
  CalendarMonsterConfig config;

  @Before
  public void setup() {
    config = new CalendarMonsterConfig();
  }

  @Test
  public void getShortAppSha1_noValue() throws Exception {
    config.getShortAppSha1();
  }

  @Test
  public void getShortAppSha1_jacked() throws Exception {
    Deencapsulation.setField(config, "appSha1", "1234");
    config.getShortAppSha1();
  }

  @Test
  public void getShortAppSha1() throws Exception {
    Deencapsulation.setField(config, "appSha1", "506af8698a5162d380192c9e722a721c064d7d37w");
    config.getShortAppSha1();
  }

}
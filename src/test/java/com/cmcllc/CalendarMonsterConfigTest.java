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

}
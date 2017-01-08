package com.cmcllc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * There is no active profile specified so the 'default' profile will be active.
 * Spring will set the default active profile to 'prod' but we have an applications.yaml file
 * that sets the default profile to 'dev'
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CalendarMonsterApplicationTests {

  /**
   * Run this test and look in the console to see how Spring is configured and which
   * Beans, etc. thinks it has (if your classes are missing they may not be configured
   * correctly).
   */
  @Test
  public void contextLoads() {
  }

}

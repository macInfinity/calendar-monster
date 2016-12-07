package com.cmcllc.parse;

import org.junit.Test;

/**
 * Created by chrismaki on 12/4/16.
 */
public class ParseLocalTimeMixedTest {

  @Test
  public void testConversion() {
    ParseLocalTimeMixed parser = new ParseLocalTimeMixed();
    Object result = parser.execute("5:15 am", null);


  }
}
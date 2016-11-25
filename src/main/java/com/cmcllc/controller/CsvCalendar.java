package com.cmcllc.controller;

import com.cmcllc.CalendarMonsterApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chrismaki on 11/20/16.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/convert")
public class CsvCalendar {
  private static final Logger logger = LoggerFactory.getLogger(CalendarMonsterApplication.class);

  @RequestMapping("/csv")
  public String convertCsvFile() {
    logger.trace("This is CsvCalendar's trace.");
    logger.debug("Finally hit the convertCsvFile method!!!");
    logger.debug("This is CsvCalendar's debug.");
    logger.info("This is CsvCalendar's info.");
    logger.warn("This is CsvCalendar's warn.");
    logger.error("This is CsvCalendar's error.");
    return "Hello world!!";
  }
}

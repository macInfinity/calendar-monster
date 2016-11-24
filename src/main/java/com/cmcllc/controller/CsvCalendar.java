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
//@RequestMapping("/convert")
public class CsvCalendar {
  public static final Logger logger = LoggerFactory.getLogger(CalendarMonsterApplication.class);

  @RequestMapping("/")
  public String convertCsvFile() {
    logger.debug("Finally hit the converCsvFile method!!!");
    return "Hello world!!";
  }
}

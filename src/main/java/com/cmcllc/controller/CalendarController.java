package com.cmcllc.controller;

import com.cmcllc.CalendarMonsterConfig;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chrismaki on 11/20/16.
 */
@RestController
@EnableAutoConfiguration
public class CalendarController {
  private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);
  private CalendarMonsterConfig config;

  @Autowired
  public CalendarController(CalendarMonsterConfig config) {
    this.config = config;
  }

}

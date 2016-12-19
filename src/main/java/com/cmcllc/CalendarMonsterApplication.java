package com.cmcllc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CalendarMonsterApplication {

  public static void main(String[] args) {
    SpringApplication.run(CalendarMonsterApplication.class, args);
  }
}

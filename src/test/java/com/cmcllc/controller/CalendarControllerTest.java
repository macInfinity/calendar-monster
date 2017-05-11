package com.cmcllc.controller;

import com.cmcllc.CalendarMonsterApplication;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by chrismaki on 12/10/16.
 * <p>
 * http://stackoverflow.com/questions/21800726/using-spring-mvc-test-to-unit-test-multipart-post-request
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CalendarMonsterApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
@Ignore
public class CalendarControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

}
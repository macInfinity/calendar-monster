package com.cmcllc.controller;

import com.cmcllc.CalendarMonsterApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by chrismaki on 12/10/16.
 * <p>
 * http://stackoverflow.com/questions/21800726/using-spring-mvc-test-to-unit-test-multipart-post-request
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CalendarMonsterApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CsvCalendarControllerTest {

  private Path offSeasonData;
  private MockMvc mockMvc;

  @Autowired
  WebApplicationContext webApplicationContext;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    offSeasonData = Paths.get(getClass().getClassLoader().getResource("OffSeason.csv").getPath());

  }

  @Test
  public void convertCsvFile() throws Exception {
    // NOTE: the "name" of this MockMultipartFile is the param name the endpoint will look for
    MockMultipartFile firstFile = new MockMultipartFile("file", offSeasonData.getFileName().toString(),
        "text/plain", Files.readAllBytes(offSeasonData));

    MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    mockMvc.perform(MockMvcRequestBuilders.fileUpload("/calendar")
        .file(firstFile))
        .andExpect(status().is(201));

  }

  @Test
  public void uploadFile() throws Exception {

  }

}
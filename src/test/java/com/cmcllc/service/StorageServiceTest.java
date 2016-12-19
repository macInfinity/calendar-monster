package com.cmcllc.service;

import com.cmcllc.CalendarMonsterConfig;
import mockit.Deencapsulation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Path;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by chrismaki on 12/10/16.
 */
public class StorageServiceTest {

  private StorageService storageService;
  private CalendarMonsterConfig calendarMonsterConfig;

  @Before
  public void setup() {
    calendarMonsterConfig = new CalendarMonsterConfig(true, 1);
    storageService = new FileSystemStorageService(calendarMonsterConfig);
    storageService.init();
  }

  @Test
  public void storeFile()  {
    MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
    Path result = storageService.storeFile(firstFile);

    assertThat(result, notNullValue());
  }

  @Test
  public void storeFileAndCleanup() throws IOException {
    Path roolLocation = Deencapsulation.getField(storageService, "rootLocationPath");
    MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain",
        "another file".getBytes());

    Path result = storageService.storeFile(firstFile);
    assertThat(result, notNullValue());
    assertThat(result.toFile().exists(), is(true));

    Deencapsulation.invoke(storageService, "cleanup");
    assertThat(roolLocation.toFile().exists(), is(false));
  }
}
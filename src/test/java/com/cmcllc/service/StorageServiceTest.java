package com.cmcllc.service;

import com.cmcllc.CalendarMonsterConfig;
import mockit.Deencapsulation;
import org.apache.commons.io.FilenameUtils;
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
  public void createFile() {
    Path result = storageService.createTempFile(null, null);
    assertThat(FilenameUtils.getExtension(result.getFileName().toString()), is("tmp"));
    Path result2 = storageService.createTempFile("prefix", null);
    assertThat(FilenameUtils.getBaseName(result2.getFileName().toString()).startsWith("prefix"), is(true));
    Path result3 = storageService.createTempFile(null, "suffix");
    assertThat(FilenameUtils.getExtension(result3.getFileName().toString()), is("suffix"));
    Path result4 = storageService.createTempFile("prefix", "suffix");
    assertThat(FilenameUtils.getBaseName(result4.getFileName().toString()).startsWith("prefix"),is(true));
    assertThat(FilenameUtils.getExtension(result4.getFileName().toString()), is("suffix"));
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
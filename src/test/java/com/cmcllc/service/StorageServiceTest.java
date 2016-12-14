package com.cmcllc.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Path;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by chrismaki on 12/10/16.
 */
public class StorageServiceTest {

  private StorageService storageService;

  @Before
  public void setup() {
    storageService = new FileSystemStorageService();
    storageService.init();
  }

  @Test
  public void storeFile()  {
    MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
    Path result = storageService.storeFile(firstFile);

    assertThat(result, notNullValue());
  }
}
package com.cmcllc.controller;

import com.cmcllc.CalendarMonsterApplication;
import com.cmcllc.service.CalendarParserService;
import com.cmcllc.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.file.Path;

/**
 * Created by chrismaki on 11/20/16.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/calendar")
public class CsvCalendarController {
  private static final Logger logger = LoggerFactory.getLogger(CalendarMonsterApplication.class);

  private StorageService storageService;

  private CalendarParserService calendarParserService;

  @Autowired
  public CsvCalendarController(StorageService storageService,
                               CalendarParserService calendarParserService) {
    this.storageService = storageService;
    this.calendarParserService = calendarParserService;
  }

  @PostMapping()
  public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file,
                                         UriComponentsBuilder uriComponentsBuilder) throws Exception {

    Path csvFile = storageService.storeFile(file);
    Path icsFile = storageService.createTempFile(null, "ics");
    calendarParserService.createCalendarFile(csvFile.toAbsolutePath().toString(), icsFile);

    UriComponents uriComponents = uriComponentsBuilder
        .path("/calendar/ics/{name}")
        .buildAndExpand(icsFile.getFileName().toString());

    return ResponseEntity.created(uriComponents.toUri()).build();
  }

  // http://stackoverflow.com/questions/12395115/spring-missing-the-extension-file
  @GetMapping("/ics/{filename}.{ext}")
  @ResponseBody
  public ResponseEntity<Resource> serveFile(@PathVariable String filename,
                                            @PathVariable String ext) {
    String path = String.format("%s.%s", filename, ext);
    Resource file = storageService.loadFileAsResource(path);
    return ResponseEntity
        .ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
        .body(file);
  }
}

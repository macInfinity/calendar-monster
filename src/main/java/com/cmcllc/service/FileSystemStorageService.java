package com.cmcllc.service;

import com.cmcllc.CalendarMonsterConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Store files for the app
 */
@Service
public class FileSystemStorageService implements StorageService {

  private static final Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

  private Path rootLocationPath;

  private CalendarMonsterConfig config;


  @Autowired
  public FileSystemStorageService(CalendarMonsterConfig config) {
    this.config = config;
  }

  @PostConstruct
  public void init() {
    try {
      rootLocationPath = Files.createTempDirectory(null);
    } catch (IOException e) {
      throw new StorageException("couldn't create root storage location.", e);
    }
    logger.info("rootLocationPath: {}", rootLocationPath.toString());
  }

  @PreDestroy
  public void cleanup() {
    if (config.isCleanupOnExitEnabled()) {
      try {
        Files.walk(rootLocationPath)
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Scheduled(fixedDelayString = "${app.cleanup.delay:600000}")
  public void removeOldFiles() {
    logger.debug("cleaning up files...");
    Instant deleteInstant = Instant.now().minus(config.getDelayToDeleteFiles(), ChronoUnit.MINUTES);
    try {
      Files.walk(rootLocationPath)
          .sorted(Comparator.reverseOrder())
          .filter(isCreatedBefore(deleteInstant))
          .map(Path::toFile)
          .filter(File::isFile)
          .peek(logEntry("removing file: {}"))
          .forEach(File::delete);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * returns filename identifier, use this name for the load methods below
   *
   * @param file
   * @return Path to the local copy of hte file
   */
  public Path storeFile(MultipartFile file) {
    Path target;
    try {
      if (file.isEmpty()) {
        throw new StorageException(String.format("File '%s' was empty, nothing to process",
            file.getOriginalFilename()));
      }
      // random filename
      target = rootLocationPath.resolve(UUID.randomUUID().toString());
      Files.copy(file.getInputStream(), target);
      logger.debug(String.format("Wrote input file: %s to %s", file.getOriginalFilename(),
          target.toString()));
    } catch (IOException e) {
      throw new StorageException("Error trying to store file: " + file.getOriginalFilename());
    }

    return target;
  }

  public Path loadFile(String filename) {
    return rootLocationPath.resolve(filename);
  }

  public Resource loadFileAsResource(String filename) {
    try {
      Path file = loadFile(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new StorageException("Could not read file: " + filename);
      }
    } catch (MalformedURLException e) {
      throw new StorageException("Could not read file: " + filename, e);
    }
  }

  public Path createTempFile(String prefix, String suffix) {
    prefix = StringUtils.stripToNull(prefix) == null ? "" : StringUtils.stripToEmpty(prefix);
    suffix = StringUtils.stripToNull(suffix) == null ? "tmp" : StringUtils.stripToEmpty(suffix);


    String fileName = String.format("%s%s.%s", prefix, UUID.randomUUID().toString(), suffix);

    if (rootLocationPath.getParent() == null) {
      throw new StorageException("Cannot create temp file, invalid prefix or suffix");
    }
    return rootLocationPath.resolve(fileName);
  }

  public static Predicate<Path> isCreatedBefore(Instant instant) {
    return p -> {
      try {
        BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);
        logger.trace("Delete date is: {}, current file create date is: {}, delete? {}",
            instant, attr.creationTime().toInstant(),
            (!attr.isDirectory() && attr.creationTime().toInstant().isBefore(instant)));
        return !attr.isDirectory() && attr.creationTime().toInstant().isBefore(instant);
      } catch (Exception e) {
        logger.warn("error checking file {}", p, e);
      }
      return false;
    };
  }

  public static Consumer<File> logEntry(String message) {
    return p -> {
      logger.trace(message, p);
    };
  }


}

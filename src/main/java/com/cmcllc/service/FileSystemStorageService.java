package com.cmcllc.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Store files for the app
 */
@Service
public class FileSystemStorageService implements StorageService {

  private static final Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

  private Path rootLocationPath;

  public FileSystemStorageService() {
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
        throw new StorageException(String.format("File {} was empty, nothing to process",
            file.getOriginalFilename()));
      }
      // random filename
      target = rootLocationPath.resolve(UUID.randomUUID().toString());
      Files.copy(file.getInputStream(), target);
      logger.info(String.format("Wrote input file: %s to %s", file.getOriginalFilename(),
          target.toString()));
    } catch (IOException e) {
      throw new StorageException("Error trying to store file: " + file.getOriginalFilename());
    }

    return target;
  }

  public Path loadFile(String filename) {
    return rootLocationPath.resolve(filename);
  }

  // TODO: needs some tests
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
    suffix = StringUtils.stripToNull(suffix) == null ? ".tmp" : StringUtils.stripToEmpty(prefix);

    String fileName = String.format("%s%s%s", prefix, UUID.randomUUID().toString(), suffix);

    // TODO: needs test
    if (rootLocationPath.getParent() == null) {
      throw new StorageException("Cannot create temp file, invalid prefix or suffix");
    }
    return rootLocationPath.resolve(fileName);
  }
}

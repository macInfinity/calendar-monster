package com.cmcllc.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * Store files for the app
 */
public interface StorageService {

  /**
   * returns filename identifier, use this name for the load methods below
   *
   * @param file
   * @return Path to the local copy of hte file
   */
  public Path storeFile(MultipartFile file);

  /**
   * Loads a flie from the storage system
   *
   * @param filename
   * @return
   */
  public Path loadFile(String filename);

  public Resource loadFileAsResource(String filename);

  public Path createTempFile(String prefix, String suffix);

  public void init();
}

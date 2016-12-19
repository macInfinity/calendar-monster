package com.cmcllc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chrismaki on 12/15/16.
 */
@Configuration
public class CalendarMonsterConfig {

  @Value("${cm.cleanup.onexit:false}")
  private boolean cleanupOnExitEnabled;

  /**
   * minutes to wait before removing old files. Default is 20 minutes.
   */
  @Value("${cm.cleanup.deletetime:1}")
  private int delayToDeleteFiles;

  @Value("${cm.cleanup.delay:600000}")
  private int fixedDelayString;


  public CalendarMonsterConfig() {}

  public CalendarMonsterConfig(boolean cleanupOnExitEnabled, int delayToDeleteFiles) {
    this.cleanupOnExitEnabled = cleanupOnExitEnabled;
    this.delayToDeleteFiles = delayToDeleteFiles;
  }

  public boolean isCleanupOnExitEnabled() {
    return cleanupOnExitEnabled;
  }

  public void setCleanupOnExitEnabled(boolean cleanupOnExitEnabled) {
    this.cleanupOnExitEnabled = cleanupOnExitEnabled;
  }

  public int getDelayToDeleteFiles() {
    return delayToDeleteFiles;
  }

  public void setDelayToDeleteFiles(int delayToDeleteFiles) {
    this.delayToDeleteFiles = delayToDeleteFiles;
  }
}

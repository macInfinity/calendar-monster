package com.cmcllc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chrismaki on 12/15/16.
 */
@Configuration
public class CalendarMonsterConfig {

  /**
   * when calendar-monster is terminated, should it remove all temporary files?
   */
  @Value("${app.cleanup.onexit:false}")
  private boolean cleanupOnExitEnabled;

  /**
   * how old, in minutes, should a file be before it will be deleted?
   */
  @Value("${app.cleanup.deletetime:1}")
  private int delayToDeleteFiles;

  /**
   * milliseconds to wait before removing old files. Default is 10 minutes.
   */
  @Value("${app.cleanup.delay:600000}")
  private int fixedDelayString;

  /**
   * the sha-1 value when the application was compiled
   */
  @Value("${app.sha-1:1234567}")
  private String appSha1;

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

  public String getAppSha1() {
    return appSha1;
  }

  public void setAppSha1(String appSha1) {
    this.appSha1 = appSha1;
  }

  public String getShortAppSha1() {
    if (StringUtils.isEmpty(appSha1) || appSha1.length() < 7) {
      return "";
    }
    // return the first 7 characters
    return appSha1.substring(0,6);
  }
}

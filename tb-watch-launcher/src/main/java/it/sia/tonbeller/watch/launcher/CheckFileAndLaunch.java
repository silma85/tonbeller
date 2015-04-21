/**
 * 
 */
package it.sia.tonbeller.watch.launcher;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interface for file checker and action launcher.
 * 
 * @author alessandro.putzu
 *
 */
public class CheckFileAndLaunch {

  private final static Logger log = LoggerFactory.getLogger(CheckFileAndLaunch.class);

  public void checkDirectoryAndLaunchBatch(Object... args) {

    final String pathToWatch = (String) args[0];
    final String batchToLaunch = (String) args[1];

    File file = new File(pathToWatch);
    if (file.exists()) {

      log.info(String.format("File %s was found.", pathToWatch));

      try {
        Runtime.getRuntime().exec("cmd /c start " + batchToLaunch);
      } catch (IOException e) {
        log.error("Errore I/O: " + e.getMessage());
      } finally {
        log.info("The conditions have been met and the batch was launched. Continuing...");
      }
    } else {
      log.info("File not found. Moving on...");
    }
  }

}

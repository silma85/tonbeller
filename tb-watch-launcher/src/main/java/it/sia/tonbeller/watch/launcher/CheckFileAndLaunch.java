/**
 * 
 */
package it.sia.tonbeller.watch.launcher;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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

  public void checkDirectoryAndLaunchBatch(final String pathToWatch, final String batchToLaunch,
          final int pollingIntervalSeconds) {

    log.info(String.format("Checking for file %s every %d seconds and launching %s when it is found...", pathToWatch,
            pollingIntervalSeconds, batchToLaunch));

    final TimerTask task = new TimerTask() {

      @Override
      public void run() {

        File file = new File(pathToWatch);
        if (file.exists()) {

          log.info(String.format("File %s was found.", pathToWatch));

          try {
            Runtime.getRuntime().exec("cmd /c start " + batchToLaunch);
          } catch (IOException e) {
            log.error("Errore I/O: " + e.getMessage());
          } finally {
            log.info("The conditions have been met and the batch was launched. Program will now exit.");
            System.exit(0);
          }
        }
      }
    };

    final Timer timer = new Timer();
    timer.schedule(task, 0, pollingIntervalSeconds * 1000);
  }

}

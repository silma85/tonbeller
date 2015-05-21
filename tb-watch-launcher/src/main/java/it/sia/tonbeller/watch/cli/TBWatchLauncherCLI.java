/**
 * 
 */
package it.sia.tonbeller.watch.cli;

import it.sia.tonbeller.watch.launcher.CheckFileAndLaunch;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

/**
 * @author alessandro.putzu
 *
 */
@Controller
public class TBWatchLauncherCLI implements CommandLineRunner {

  private final static Logger log = LoggerFactory.getLogger(TBWatchLauncherCLI.class);

  private final static int DEFAULT_POLLING_INTERVAL = 2;

  @Value("${watch.path}")
  public String path;

  @Value("${watch.batch}")
  public String batch;

  @Value("${watch.interval}")
  public Integer interval;

  private final static Timer timer = new Timer();

  /**
   * @param args
   * @throws IOException
   */
  @Override
  public void run(final String... args) throws IOException {

    log.info("Program launched.");

    this.validate();

    final TimerTask task = new TimerTask() {

      @Override
      public void run() {

        log.info(String.format("Checking for file %s and launching %s when it is found...",
            path, batch));

        new CheckFileAndLaunch().checkDirectoryAndLaunchBatch(path, batch, interval);

      }
    };

    timer.schedule(task, 0, interval * 1000);
  }

  public static void stop(String[] args) {
    log.info("The service is stopping...");
    timer.cancel();
  }

  private boolean validate() {

    // First argument must not be a directory.
    if (StringUtils.isEmpty(path)
        || path.endsWith(System.getProperty("file.separator"))) {
      log.error("Fatal: Path to watch must be a file.");
      log.error("Change the relevant parameter in the property file.");
      System.exit(2);
    }

    // Second argument must exist.
    File batchFile = new File(batch);
    if (!batchFile.exists()) {
      log.error("Fatal: Batch to launch must exist.");
      log.error("Change the relevant parameter in the property file.");
      System.exit(3);
    }

    if (interval == 0) {
      interval = DEFAULT_POLLING_INTERVAL;
    }

    return true;
  }
}

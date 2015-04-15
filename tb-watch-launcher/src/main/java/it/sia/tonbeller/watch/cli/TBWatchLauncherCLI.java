/**
 * 
 */
package it.sia.tonbeller.watch.cli;

import it.sia.tonbeller.watch.launcher.CheckFileAndLaunch;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alessandro.putzu
 *
 */
public class TBWatchLauncherCLI {

  private final static Logger log = LoggerFactory.getLogger(TBWatchLauncherCLI.class);

  private final static int DEFAULT_POLLING_INTERVAL = 2;

  /**
   * @param args
   */
  public static void main(String[] args) {

    log.info("Program launched.");

    // Preliminary check: has at least two arguments.
    if (args.length < 2) {
      log.warn("Usage: TBWatchLauncher.exe <pathToWatch> <batchToLaunch> [<pollingIntervalSeconds>]");
      System.exit(1);
    }

    // First argument must not be a directory.
    final String pathToWatch = args[0];
    if (pathToWatch.endsWith(System.getProperty("file.separator"))) {
      log.error("Fatal: Path to watch must be a file.");
      System.exit(2);
    }

    // Second argument must exist.
    final String batchToLaunch = args[1];
    File batch = new File(batchToLaunch);
    if (!batch.exists()) {
      log.error("Fatal: Batch to launch must exist.");
      System.exit(3);
    }

    // If third argument exists and is a number, use it as the number of seconds.
    String pollingInterval = null;
    int pollingIntervalSeconds = 0;
    if (args.length > 2) {
      pollingInterval = args[2];

      try {
        pollingIntervalSeconds = Integer.parseInt(pollingInterval);
      } catch (NumberFormatException e) {
        pollingIntervalSeconds = DEFAULT_POLLING_INTERVAL;
      }
    } else {
      pollingIntervalSeconds = DEFAULT_POLLING_INTERVAL;
    }

    new CheckFileAndLaunch().checkDirectoryAndLaunchBatch(pathToWatch, batchToLaunch, pollingIntervalSeconds);
  }
}

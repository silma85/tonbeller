/**
 * 
 */
package it.sia.tonbeller.watch.cli;

import it.sia.tonbeller.watch.launcher.CheckFileAndLaunch;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

/**
 * @author alessandro.putzu
 *
 */
@Controller
public class TBWatchLauncherCLI implements CommandLineRunner {

  private final static Logger log = LoggerFactory.getLogger(TBWatchLauncherCLI.class);

  private final static int DEFAULT_POLLING_INTERVAL = 2;

  /**
   * @param args
   * @throws IOException
   */
  @Override
  public void run(final String... args) throws IOException {

    log.info("Program launched.");

    Object[] validatedArgs = getAndValidateArgs(args);

    final TimerTask task = new TimerTask() {

      @Override
      public void run() {

        Object[] validatedArgs;
        try {
          validatedArgs = getAndValidateArgs(args);
          log.info(String.format("Checking for file %s and launching %s when it is found...",
              validatedArgs[0], validatedArgs[1]));

          new CheckFileAndLaunch().checkDirectoryAndLaunchBatch(validatedArgs);
        } catch (IOException e) {
          log.error("Error while reading properties. ", e);
        }
      }
    };

    final Timer timer = new Timer();
    timer.schedule(task, 0, (Integer) validatedArgs[2] * 1000);
  }

  private Object[] getAndValidateArgs(String... args) throws IOException {
    Object[] validatedArgs = null;

    // Preliminary check: program properties.
    String propertiesFile = System.getProperty("watch.properties");
    if (propertiesFile != null) {

      InputStream is = getClass().getClassLoader().getResourceAsStream(propertiesFile);

      Properties properties = new Properties();
      properties.load(is);
      log.info(Arrays.deepToString(properties.entrySet().toArray()));

      validatedArgs = argsFromProperties(properties);
    } else {
      validatedArgs = validateArgs(args, false);
    }
    return validatedArgs;
  }

  private Object[] argsFromProperties(Properties properties) {
    Object[] validatedArgs;
    String pathToWatch = properties.getProperty("watchdog.path_to_watch");
    String batchToLaunch = properties.getProperty("watchdog.batch_to_launch");
    String pollingInterval = properties
        .getProperty("watchdog.polling_interval");

    validatedArgs = validateArgs(new String[] { pathToWatch, batchToLaunch, pollingInterval }, true);
    return validatedArgs;
  }

  private Object[] validateArgs(String[] args, boolean reloadable) {
    // Preliminary check: has at least two arguments.
    Object[] validatedArgs = new Object[3];
    if (args.length < 2) {
      log.warn("Usage: TBWatchLauncher.exe <pathToWatch> <batchToLaunch> [<pollingIntervalSeconds>]");
      System.exit(1);
    }

    // First argument must not be a directory.
    validatedArgs[0] = args[0];
    if (((String) validatedArgs[0]).endsWith(System.getProperty("file.separator"))) {
      log.error("Fatal: Path to watch must be a file.");

      if (!reloadable) {
        System.exit(2);
      } else {
        log.error("Change the relevant parameter in the property file.");
      }
    }

    // Second argument must exist.
    validatedArgs[1] = args[1];
    File batch = new File((String) validatedArgs[1]);
    if (!batch.exists()) {
      log.error("Fatal: Batch to launch must exist.");

      if (!reloadable) {
        System.exit(3);
      } else {
        log.error("Change the relevant parameter in the property file.");
      }
    }

    // If third argument exists and is a number, use it as the number of seconds.
    String pollingInterval = null;
    validatedArgs[2] = 0;
    if (args.length > 2) {
      pollingInterval = args[2];

      try {
        validatedArgs[2] = Integer.parseInt(pollingInterval);
      } catch (NumberFormatException e) {
        validatedArgs[2] = DEFAULT_POLLING_INTERVAL;
      }
    } else {
      validatedArgs[2] = DEFAULT_POLLING_INTERVAL;
    }

    return validatedArgs;
  }
}

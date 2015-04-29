/**
 * 
 */
package it.sia.stresstest;

import it.sia.stresstest.beans.TestKYCScoring;
import it.sia.stresstest.beans.TestLocal;
import it.sia.stresstest.beans.TestWSSearch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alessandro.putzu
 *
 */
public class StressTestApplication {

  private static final Logger log = LoggerFactory.getLogger(StressTestApplication.class);

  public static final Properties props = new Properties();

  /**
   * Application unique entry point.
   * 
   * @param args
   */
  public static void main(final String... args) {

    if (args == null || args.length == 0) {
      log.error("Necessario almeno il nome del metodo del webservice da chiamare.");
      System.exit(1);
    }

    // Caricare properties.
    try {
      InputStream is = StressTestApplication.class.getResourceAsStream("/wsstresstest.properties");
      props.load(is);
    } catch (IOException e) {
      log.error("Impossibile trovare il file wsstresstest.properties, assicurarsi che esista nella stessa directory dell'eseguibile.");
      System.exit(3);
    }

    String method = null;
    if (args.length > 0) {
      method = args[0];
    }

    switch (method) {
    case "search":
      StressTestStart.start(new TestWSSearch());
      break;

    case "scoring":
      StressTestStart.start(new TestKYCScoring());
      break;

    case "test":
      StressTestStart.start(new TestLocal());
      break;

    default:

      log.warn("Metodo non supportato.");
      System.exit(2);

      break;
    }
  }

}

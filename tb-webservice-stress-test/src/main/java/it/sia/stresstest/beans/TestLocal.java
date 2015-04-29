/**
 * 
 */
package it.sia.stresstest.beans;

import it.sia.stresstest.StressTestApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alessandro.putzu
 *
 */
public class TestLocal implements TestWebService {

  private static final Logger log = LoggerFactory.getLogger(TestLocal.class);

  /*
   * (non-Javadoc)
   * 
   * @see it.sia.stresstest.beans.TestWebService#start()
   */
  @Override
  public String start() {

    try {

      final URL url = new URL(StressTestApplication.props.getProperty("test.url"));
      final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Accept", "application/json");

      if (connection.getResponseCode() != 200) {
        throw new RuntimeException("Failed : HTTP error code : "
            + connection.getResponseCode());
      }

      BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

      String line = null;
      StringBuilder result = new StringBuilder();
      while ((line = br.readLine()) != null) {
        result.append(line);
      }

      connection.disconnect();

      return result.toString();

    } catch (MalformedURLException e) {
      log.error("Errore nella lettura URL", e);
    } catch (IOException e) {
      log.error("Errore di connessione all'URL", e);
    }

    return null;
  }
}

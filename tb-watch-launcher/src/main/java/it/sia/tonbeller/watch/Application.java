/**
 * 
 */
package it.sia.tonbeller.watch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;

/**
 * @author alessandro.putzu
 *
 */
@SpringBootApplication(exclude = { JmxAutoConfiguration.class })
public class Application {

  public static void main(String[] args) {
    System.setProperty("spring.config.name", "watch");
    SpringApplication.run(Application.class, args);
  }
}

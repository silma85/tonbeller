/**
 * 
 */
package it.sia.tonbeller.embargo.connector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

/**
 * Main runner application
 * 
 * @author alessandro.putzu
 *
 */
@ImportResource("classpath:root-context.xml")
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(Application.class);
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}

/**
 * 
 */
package it.sia.tonbeller.customersearch.test;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.util.Base64Utils;
import org.springframework.util.StreamUtils;

/**
 * @author alessandro.putzu
 *
 */
public class TestBase64Enc {

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {

    InputStream is = TestBase64Enc.class.getResourceAsStream("/test.txt");
    byte[] bytes = StreamUtils.copyToByteArray(is);
    byte[] encoded = Base64Utils.encode(bytes);

    System.out.println(encoded);

  }

}

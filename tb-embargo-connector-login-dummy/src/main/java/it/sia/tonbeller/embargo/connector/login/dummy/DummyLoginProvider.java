/**
 * 
 */
package it.sia.tonbeller.embargo.connector.login.dummy;

import it.sia.tonbeller.embargo.connector.login.domain.User;

import java.util.Arrays;

/**
 * @author alessandro.putzu
 *
 */
public class DummyLoginProvider {

  public User loadUsername(String username) {
    DummyLoginData dummy = DummyLoginData.get(username);
    return new User(dummy.getUsername(), dummy.getPassword(), Arrays.asList(dummy.getRoles()));
  }

}

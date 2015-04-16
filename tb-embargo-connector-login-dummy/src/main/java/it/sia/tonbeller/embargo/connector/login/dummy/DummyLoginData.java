/**
 * 
 */
package it.sia.tonbeller.embargo.connector.login.dummy;

import lombok.Getter;

/**
 * This contains dummy login data.
 * 
 * @author alessandro.putzu
 *
 */
@Getter
public enum DummyLoginData {

  USER("user", "secret", "ROLE_USER"),
  ADMIN("admin", "admin", "ROLE_ADMIN"),
  GOD("kami", "kami", "ROLE_USER", "ROLE_ADMIN");

  private String username;

  private String password;

  private String[] roles;

  private DummyLoginData(String username, String password, String... roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  public static boolean exists(String username) {
    for (DummyLoginData datum : DummyLoginData.values()) {
      if (username.equals(datum.getUsername())) {
        return true;
      }
    }

    return false;
  }

  public static DummyLoginData get(String username) {
    for (DummyLoginData datum : DummyLoginData.values()) {
      if (username.equals(datum.getUsername())) {
        return datum;
      }
    }

    return null;
  }
}

/**
 * 
 */
package it.sia.tonbeller.embargo.connector.login.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * @author alessandro.putzu
 *
 */
@Data
@NoArgsConstructor
public class User {

  private String username;

  private String password;

  private Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

  public User(String username, String password, List<String> authorities) {
    this.username = username;
    this.password = password;

    for (String a : authorities) {
      getAuthorities().add(new SimpleGrantedAuthority(a));
    }
  }
}

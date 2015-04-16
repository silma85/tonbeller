/**
 * 
 */
package it.sia.tonbeller.embargo.connector.security;

import it.sia.tonbeller.embargo.connector.login.domain.User;
import it.sia.tonbeller.embargo.connector.login.dummy.DummyLoginProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of User Details Service
 * 
 * @author alessandro.putzu
 *
 */
@Configuration
@Service("userDetailsService")
public class SecurityUserDetailsService implements UserDetailsService {

  @Autowired
  private DummyLoginProvider loginProvider;

  /*
   * (non-Javadoc)
   * 
   * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = loginProvider.loadUsername(username);

    if (user != null) {
      return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
          user.getAuthorities());
    }

    return null;
  }
}

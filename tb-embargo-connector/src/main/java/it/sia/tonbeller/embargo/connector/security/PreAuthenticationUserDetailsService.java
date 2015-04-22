/**
 * 
 */
package it.sia.tonbeller.embargo.connector.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import com.tonbeller.tblogin.extern.ExternalLoginOk;

/**
 * @author alessandro.putzu
 *
 */
@Service("userDetailsService")
public class PreAuthenticationUserDetailsService implements
    AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

  @Override
  public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {

    final ExternalLoginOk extLogin = (ExternalLoginOk) token.getPrincipal();
    final String username = extLogin.getUser();

    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    for (String role : extLogin.getRoles().split(";")) {
      authorities.add(new SimpleGrantedAuthority(role));
    }

    return new User(username, String.valueOf(username.hashCode()), authorities);
  }

}

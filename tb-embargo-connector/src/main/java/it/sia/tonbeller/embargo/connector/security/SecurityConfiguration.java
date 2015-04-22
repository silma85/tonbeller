/**
 * 
 */
package it.sia.tonbeller.embargo.connector.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

/**
 * Security auto-config bean.
 * 
 * @author alessandro.putzu
 *
 */
@EnableWebSecurity
@Configuration
@ImportResource("classpath:root-security.xml")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private PreAuthenticatedAuthenticationProvider preAuthenticatedProvider;

  @Autowired
  private PreAuthenticationFilter preAuthFilter;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder authorization) throws Exception {
    authorization.authenticationProvider(preAuthenticatedProvider);
  }

  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
        // We want: resources and trivia to be permitted.
        .antMatchers("/resources/**").permitAll()
        // And any other request to require authentication.
        .anyRequest().authenticated()
        // And form login to require none.
        .and().addFilter(preAuthFilter);
  }
}
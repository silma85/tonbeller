/**
 * 
 */
package it.sia.tonbeller.embargo.connector.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security auto-config bean.
 * 
 * @author alessandro.putzu
 *
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private SecurityUserDetailsService userDetailsService;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder authorization) throws Exception {
    // authorization.inMemoryAuthentication()
    // .withUser("admin").password("admin").roles("USER", "ADMIN").and()
    // .withUser("user").password("password").roles("USER");
    authorization.userDetailsService(userDetailsService);
  }

  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
        // We want: resources and trivia to be permitted.
        .antMatchers("/resources/**").permitAll()
        // And any other request to require authentication.
        .anyRequest().authenticated()
        // And form login to require none.
        .and().formLogin().loginPage("/login").permitAll();
  }
}
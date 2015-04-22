/**
 * 
 */
package it.sia.tonbeller.embargo.connector.security;

import it.racomputer.sso.XSSOLoginBPO;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.tonbeller.tblogin.extern.ExternalLoginResult;
import com.tonbeller.tblogin.extern.ExternalLoginSkip;

/**
 * @author alessandro.putzu
 *
 */
public class PreAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

  private final static Logger log = LoggerFactory.getLogger(PreAuthenticationFilter.class);

  @Override
  protected ExternalLoginResult getPreAuthenticatedCredentials(HttpServletRequest request) {
    try {
      return new XSSOLoginBPO().loginExternSSO(request);
    } catch (Exception e) {
      log.error("Error while retrieving SSO login", e);
    }

    return new ExternalLoginSkip();
  }

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    try {
      return new XSSOLoginBPO().loginExternSSO(request);
    } catch (Exception e) {
      log.error("Error while retrieving SSO login", e);
    }

    return new ExternalLoginSkip();
  }

}

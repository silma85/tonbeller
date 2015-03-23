/**
 * 
 */
package it.sia.tonbeller.customersearch.exception;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller for main events
 * 
 * @author alessandro.putzu
 *
 */
@ControllerAdvice
public class GlobalExceptionAdvisor {

  @ExceptionHandler({ Exception.class })
  @ResponseBody
  public Exception error_generic(final Exception e) throws Exception {

    if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
      throw e;
    }

    return e;
  }
}

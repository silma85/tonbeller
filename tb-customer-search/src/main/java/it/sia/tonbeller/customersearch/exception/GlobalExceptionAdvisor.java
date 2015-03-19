/**
 * 
 */
package it.sia.tonbeller.customersearch.exception;

import java.io.IOException;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for main events
 * 
 * @author alessandro.putzu
 *
 */
@ControllerAdvice
public class GlobalExceptionAdvisor {

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({ IOException.class })
  public ModelAndView error(final IOException e) {

    ModelAndView model = new ModelAndView();
    model.setViewName("error");
    model.addObject("message", "Error'd: " + e.getMessage());

    return model;
  }

  @ExceptionHandler({ Exception.class })
  public ModelAndView error_generic(final Exception e) throws Exception {

    if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
      throw e;
    }

    ModelAndView model = new ModelAndView();
    model.setViewName("error");
    model.addObject("message", e.getMessage());

    return model;
  }
}

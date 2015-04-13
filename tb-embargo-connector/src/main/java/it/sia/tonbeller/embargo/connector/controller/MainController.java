/**
 * 
 */
package it.sia.tonbeller.embargo.connector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author alessandro.putzu
 *
 */
@Controller
@RequestMapping("/main")
public class MainController {

  public Model index(final Model model) {

    model.addAttribute("message", "This is another message!");

    return model;
  }

}

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
public class HomeController {

  @RequestMapping("/main")
  public String main(final Model model) {

    model.addAttribute("message", "This is message!");
    model.addAttribute("user", String.format("Logged in user is %s", "eg"));

    return "main";
  }

  @RequestMapping("/login")
  public String login(final Model model) {
    return "login";
  }
}

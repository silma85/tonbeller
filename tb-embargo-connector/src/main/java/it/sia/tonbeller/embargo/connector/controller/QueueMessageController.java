/**
 * 
 */
package it.sia.tonbeller.embargo.connector.controller;

import it.sia.tonbeller.embargo.connector.repository.FailedMessageRepository;
import it.sia.tonbeller.embargo.connector.repository.InboundMessageRepository;
import it.sia.tonbeller.embargo.connector.repository.OutboundMessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author alessandro.putzu
 *
 */
@Controller
@RequestMapping("/messages")
public class QueueMessageController {

  @Autowired
  private InboundMessageRepository inboundRepository;

  @Autowired
  private OutboundMessageRepository outboundRepository;

  @Autowired
  private FailedMessageRepository failedRepository;

  @RequestMapping("/inbound")
  public String inbound(final Model model) {

    model.addAttribute("inbound", inboundRepository.findAll());

    return "messages/inbound";
  }

  @RequestMapping("/outbound")
  public String outbound(final Model model) {

    model.addAttribute("outbound", outboundRepository.findAll());

    return "messages/outbound";
  }

  @RequestMapping("/failed")
  public String failed(final Model model) {

    model.addAttribute("failed", failedRepository.findAll());

    return "messages/failed";
  }

}

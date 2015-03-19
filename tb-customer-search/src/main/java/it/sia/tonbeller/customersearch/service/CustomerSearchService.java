package it.sia.tonbeller.customersearch.service;

import it.sia.tonbeller.customersearch.domain.Customer;
import it.sia.tonbeller.customersearch.domain.CustomerData;
import it.sia.tonbeller.customersearch.mock.MockCustomerList;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerSearchService {

  private final AtomicLong counter = new AtomicLong();
  private final static Logger log = LoggerFactory.getLogger(CustomerSearchService.class);

  @RequestMapping("/search/cf/{cf}/ndg/{ndg}")
  public Customer searchByParams(final Model model, final @PathVariable("cf") String cf,
          final @PathVariable("ndg") String ndg) {

    return new Customer(counter.incrementAndGet(), "Mario", "Rossi", cf, ndg);
  }

  @RequestMapping("/search")
  public List<CustomerData> searchList(final Model model) {

    return MockCustomerList.list();
  }

}

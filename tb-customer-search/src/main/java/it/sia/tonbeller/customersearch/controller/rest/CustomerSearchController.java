package it.sia.tonbeller.customersearch.controller.rest;

import it.sia.tonbeller.customersearch.business.domain.Customer;
import it.sia.tonbeller.customersearch.business.domain.CustomerDocument;
import it.sia.tonbeller.customersearch.business.repository.CustomerRepository;
import it.sia.tonbeller.customersearch.exception.CustomerSearchException;
import it.sia.tonbeller.customersearch.test.TestBase64Enc;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerSearchController {

  private final AtomicLong counter = new AtomicLong();

  private final static Logger log = LoggerFactory.getLogger(CustomerSearchController.class);

  @Autowired
  private CustomerRepository repository;

  @RequestMapping("/search/ndg/{ndg}/cf/{cf}")
  public Customer searchByParams(final Model model, final @PathVariable("cf") String cf,
          final @PathVariable("ndg") String ndg) throws CustomerSearchException {

    Customer customer = repository.findByCfAndNdg(cf, ndg);

    if (customer == null) {
      throw new CustomerSearchException(String.format("Not found: CF = %s, NDG = %s", cf, ndg));
    }

    return customer;
  }

  @RequestMapping("/search/ndg/{ndg}")
  public Customer searchByNdg(final Model model, final @PathVariable("ndg") String ndg) throws CustomerSearchException,
          IOException {

    Customer customer = new Customer();

    String name = "Hydrangeas.jpg";

    InputStream is = TestBase64Enc.class.getResourceAsStream("/" + name);
    byte[] bytes = StreamUtils.copyToByteArray(is);
    byte[] encoded = Base64Utils.encode(bytes);

    log.debug(String.valueOf(encoded));

    customer.setCf("AAAAAJAJJAJAK");

    CustomerDocument cd = new CustomerDocument();
    cd.setBase64(encoded);
    cd.setName(name);
    customer.getDocuments().add(cd);

    customer.setId(counter.incrementAndGet());
    customer.setNdg(ndg);
    customer.setName("Tony");
    customer.setSurname("Paragoni");

    return customer;
  }

  @RequestMapping("/search")
  public Iterable<Customer> searchList(final Model model) {

    return repository.findAll();
  }

}

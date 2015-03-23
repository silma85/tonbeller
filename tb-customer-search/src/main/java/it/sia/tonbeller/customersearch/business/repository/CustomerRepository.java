package it.sia.tonbeller.customersearch.business.repository;

import it.sia.tonbeller.customersearch.business.domain.Customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

  public Customer findByCfAndNdg(@Param("cf") String cf, @Param("ndg") String ndg);

}

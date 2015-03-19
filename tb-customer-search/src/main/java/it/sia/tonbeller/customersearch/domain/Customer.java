/**
 * 
 */
package it.sia.tonbeller.customersearch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Customer domain object
 * 
 * @author alessandro.putzu
 *
 */
@Getter
@AllArgsConstructor
public class Customer {

  private long id;

  private String name;

  private String surname;

  private String cf;

  private String ndg;

}

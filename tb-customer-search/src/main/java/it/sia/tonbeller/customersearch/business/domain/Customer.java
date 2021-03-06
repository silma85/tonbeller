/**
 * 
 */
package it.sia.tonbeller.customersearch.business.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Customer domain object
 * 
 * @author alessandro.putzu
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends AbstractRootDomain {

  private static final transient long serialVersionUID = 2976277458177011578L;

  private String name;

  private String surname;

  private String cf;

  private String ndg;

  @Transient
  private List<CustomerDocument> documents = new ArrayList<CustomerDocument>();
}

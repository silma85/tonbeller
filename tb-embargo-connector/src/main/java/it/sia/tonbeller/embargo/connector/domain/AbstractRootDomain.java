/**
 * 
 */
package it.sia.tonbeller.embargo.connector.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

/**
 * Abstract Root Domain
 * 
 * @author alessandro.putzu
 *
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractRootDomain implements Serializable {

  private static final long serialVersionUID = 8754699767824631509L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "`ID`")
  private Long id;

}

/**
 * 
 */
package it.sia.tonbeller.embargo.connector.domain;

import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author alessandro.putzu
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class AbstractQueueMessage extends AbstractRootDomain {

  private static final long serialVersionUID = 7316058046686081633L;

  private String messageId;

  private String content;
}

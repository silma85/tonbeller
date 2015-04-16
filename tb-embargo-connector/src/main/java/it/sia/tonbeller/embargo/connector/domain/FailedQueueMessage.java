/**
 * 
 */
package it.sia.tonbeller.embargo.connector.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author alessandro.putzu
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "`FAILED_MESSAGES`")
public class FailedQueueMessage extends AbstractQueueMessage {

  private static final long serialVersionUID = -4840625566203576559L;

  private Date dtDiscarded;
}

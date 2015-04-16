package it.sia.tonbeller.embargo.connector.repository;

import it.sia.tonbeller.embargo.connector.domain.OutboundQueueMessage;

import org.springframework.data.repository.CrudRepository;

public interface OutboundMessageRepository extends CrudRepository<OutboundQueueMessage, Long> {

}

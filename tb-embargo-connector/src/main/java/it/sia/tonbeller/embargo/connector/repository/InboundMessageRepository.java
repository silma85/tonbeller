package it.sia.tonbeller.embargo.connector.repository;

import it.sia.tonbeller.embargo.connector.domain.InboundQueueMessage;

import org.springframework.data.repository.CrudRepository;

public interface InboundMessageRepository extends CrudRepository<InboundQueueMessage, Long> {

}

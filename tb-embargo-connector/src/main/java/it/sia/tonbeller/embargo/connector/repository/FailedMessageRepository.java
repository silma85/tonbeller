package it.sia.tonbeller.embargo.connector.repository;

import it.sia.tonbeller.embargo.connector.domain.FailedQueueMessage;

import org.springframework.data.repository.CrudRepository;

public interface FailedMessageRepository extends CrudRepository<FailedQueueMessage, Long> {

}

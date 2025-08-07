package org.mine.kb.spring.data.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.mine.kb.spring.data.jpa.model.*;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
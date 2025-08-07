package org.mine.kb.spring.data.jpa.repository;

import org.mine.kb.spring.data.jpa.configuration.SpringDataConfiguration;
import org.mine.kb.spring.data.jpa.model.Message;
import org.mine.kb.spring.data.jpa.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.testcontainers.containers.PostgreSQLContainer;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringDataConfiguration.class })
class MessageRepositoryTest {

	@Autowired
	private MessageRepository messageRepository;

	public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:14.12")
			.withDatabaseName("test-hibernate")
			.withUsername("user")
			.withPassword("password");

	@BeforeAll
	static void startContainer() {
		postgresContainer.start();
	}

	@AfterAll
	static void stopContainer() {
		postgresContainer.stop();
	}

	@Test
	public void storeLoadMessage() {
		Message message = new Message();
		message.setText("Hello World from Spring Data JPA!");

		messageRepository.save(message);

		List<Message> messages = (List<Message>) messageRepository.findAll();

		assertAll(
				() -> assertEquals(1, messages.size()),
				() -> assertEquals("Hello World from Spring Data JPA!", messages.get(0).getText()));

	}
}

package org.mine.kb.spring.data.jpa;

import java.util.List;

import org.mine.kb.spring.data.jpa.model.Currency;
import org.mine.kb.spring.data.jpa.repository.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiCatalogApplication implements CommandLineRunner {
	private static Logger LOG = LoggerFactory
			.getLogger(ApiCatalogApplication.class);

	private final CurrencyRepository repository;

	public ApiCatalogApplication(CurrencyRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiCatalogApplication.class, args);
	}

	@Override
	public void run(String... args) {
		LOG.info("EXECUTING : command line runner");

		List<Currency> Currencies = List.of(
				new Currency(null, "Currency 1", null, null, 0),
				new Currency(null, "Currency 2", null, null, 0),
				new Currency(null, "Currency 3", null, null, 0));
				
		repository.saveAll(Currencies);
	}
}

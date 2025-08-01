package org.mine.kb.spring.data.jpa.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mine.kb.spring.data.jpa.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CurrencyRepositoryTest {

	@Autowired
	private CurrencyRepository currencyRepository;

	private Currency currency1;
	private Currency currency2;
	private Currency currency3;

	@BeforeEach
	void setUp() {
		currencyRepository.deleteAll();

		currency1 = new Currency(null, "USD", "Dollar", true, 2);
		currency2 = new Currency(null, "EUR", "Euro", true, 2);
		currency3 = new Currency(null, "JPY", "Yen", false, 0);

		currencyRepository.save(currency1);
		currencyRepository.save(currency2);
		currencyRepository.save(currency3);
	}

	@Test
	void testSaveAndFindById() {
		Currency saved = currencyRepository.save(new Currency(null, "GBP", "Pound", true, 2));
		Optional<Currency> found = currencyRepository.findById(saved.getId());
		assertThat(found).isPresent();
		assertThat(found.get().getCode()).isEqualTo("GBP");
	}

	@Test
	void testFindAll() {
		List<Currency> all = (List<Currency>) currencyRepository.findAll();
		assertThat(all).hasSize(3);
	}

	@Test
	void testDeleteById() {
		currencyRepository.deleteById(currency1.getId());
		Optional<Currency> found = currencyRepository.findById(currency1.getId());
		assertThat(found).isNotPresent();
	}

	@Test
	void testFindByCode() {
		List<Currency> result = currencyRepository.findByCode("USD");
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getDescription()).isEqualTo("Dollar");
	}

	@Test
	void testFindByCodeAndDescription() {
		List<Currency> result = currencyRepository.findByCodeAndDescription("EUR", "Euro");
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getCode()).isEqualTo("EUR");
	}

	@Test
	void testFindByDescriptionOrderByCodeAsc() {
		List<Currency> result = currencyRepository.findByDescriptionOrderByCodeAsc("Dollar");
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getCode()).isEqualTo("USD");
	}

	@Test
	void testFindByDescriptionOrderByCodeDesc() {
		List<Currency> result = currencyRepository.findByDescriptionOrderByCodeDesc("Dollar");
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getCode()).isEqualTo("USD");
	}

	@Test
	void testRetrieveByCode() {
		Currency result = currencyRepository.retrieveByCode("JPY");
		assertThat(result).isNotNull();
		assertThat(result.getDescription()).isEqualTo("Yen");
	}
}
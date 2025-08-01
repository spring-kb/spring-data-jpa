package org.mine.kb.spring.data.jpa.repository;

import org.mine.kb.spring.data.jpa.model.Currency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
	// General queries
	List<Currency> findByCode(String code);
	List<Currency> findByCodeAndDescription(String code, String description);

	// Order queries
	List<Currency> findByDescriptionOrderByCodeAsc(String description);
	List<Currency> findByDescriptionOrderByCodeDesc(String description);
	
	// Manual query
	@Query("SELECT c FROM Currency c where c.code = :code")
	Currency retrieveByCode(@Param("code") String code);
}

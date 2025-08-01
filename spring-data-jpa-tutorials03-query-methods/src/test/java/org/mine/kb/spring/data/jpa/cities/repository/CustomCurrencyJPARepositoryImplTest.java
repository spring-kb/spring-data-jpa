package org.mine.kb.spring.data.jpa.cities.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mine.kb.spring.data.jpa.cities.model.Currency;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomCurrencyJPARepositoryImplTest {

    @Autowired
    private EntityManager em;
    private CustomCurrencyJPARepositoryImpl customRepo;

    @BeforeEach
    void setUp() {
        customRepo = new CustomCurrencyJPARepositoryImpl(em);
        // Insert test data
        Currency usd = new Currency();
        usd.setCode("USD");
        usd.setDescription("US Dollar");
        em.persist(usd);
        Currency eur = new Currency();
        eur.setCode("EUR");
        eur.setDescription("Euro");
        em.persist(eur);
        em.flush();
    }

    @Test
    void testRetrieveByCode_found() {
        List<Currency> result = customRepo.retrieveByCode("USD");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCode()).isEqualTo("USD");
        assertThat(result.get(0).getDescription()).isEqualTo("US Dollar");
    }

    @Test
    void testRetrieveByCode_notFound() {
        List<Currency> result = customRepo.retrieveByCode("JPY");
        assertThat(result).isEmpty();
    }
}

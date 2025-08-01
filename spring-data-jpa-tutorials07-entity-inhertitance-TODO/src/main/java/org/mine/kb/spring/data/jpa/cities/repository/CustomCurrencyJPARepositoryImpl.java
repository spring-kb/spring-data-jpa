package org.mine.kb.spring.data.jpa.cities.repository;

import java.util.List;

import org.mine.kb.spring.data.jpa.cities.model.Currency;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class CustomCurrencyJPARepositoryImpl implements CustomCurrencyJPARepository {

    EntityManager em;

    public CustomCurrencyJPARepositoryImpl(EntityManager em) {
        this.em = em;
    }

    // Example of a Custom Query Using the Criteria API
    public List<Currency> retrieveByCode(String code) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Currency> cq = cb.createQuery(Currency.class);
        // You need to define the main entity
        Root<Currency> currency = cq.from(Currency.class);
        // Define all the conditions of the query
        Predicate codePredicate = cb.equal(currency.get("code"), code);
        // You can have more than one where clause
        cq.where(codePredicate);
        // Create the query and after that executed
        TypedQuery<Currency> query = em.createQuery(cq);
        return query.getResultList();
    }
}
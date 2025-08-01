package org.mine.kb.spring.data.jpa.cities.repository;

import java.util.List;

import org.mine.kb.spring.data.jpa.cities.model.Currency;

public interface CustomCurrencyJPARepository {
    List<Currency> retrieveByCode(String code);
}
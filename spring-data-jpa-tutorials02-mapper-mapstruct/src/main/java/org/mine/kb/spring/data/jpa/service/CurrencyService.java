package org.mine.kb.spring.data.jpa.service;

import org.mine.kb.spring.data.jpa.dto.CurrencyDTO;
import org.mine.kb.spring.data.jpa.mapper.ApiMapper;
import org.mine.kb.spring.data.jpa.model.Currency;
import org.mine.kb.spring.data.jpa.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyService {

	CurrencyRepository repository;

	public CurrencyService(CurrencyRepository repository) {
		this.repository = repository;
	}

	public CurrencyDTO getById(Long id) {
		CurrencyDTO response = null;
		Optional<Currency> currency = repository.findById(id);

		if (currency.isPresent()) {
			response = ApiMapper.INSTANCE.entityToDTO(currency.get());
		}

		return response;
	}

	public CurrencyDTO save(CurrencyDTO currencyDTO) {
		Currency currency = repository.save(ApiMapper.INSTANCE.DTOToEntity(currencyDTO));
		return ApiMapper.INSTANCE.entityToDTO(currency);
	}

	public CurrencyDTO update(CurrencyDTO currencyDTO) {
		Currency currency = repository.save(ApiMapper.INSTANCE.DTOToEntity(currencyDTO));
		return ApiMapper.INSTANCE.entityToDTO(currency);
	}

	public void delete(Long id) {
		Optional<Currency> currency = repository.findById(id);

		if (currency.isPresent()) {
			repository.delete(null);
		}

	}
}

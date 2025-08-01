package org.mine.kb.spring.data.jpa.mapper;

import org.mine.kb.spring.data.jpa.dto.CurrencyDTO;
import org.mine.kb.spring.data.jpa.model.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ApiMapper {

    ApiMapper INSTANCE = Mappers.getMapper( ApiMapper.class );

    CurrencyDTO entityToDTO(Currency currency);

    Currency DTOToEntity(CurrencyDTO currency);
}

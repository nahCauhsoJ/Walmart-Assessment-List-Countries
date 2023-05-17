package com.sample.data_layer.mapper

import com.example.domain_layer.model.Currency
import com.example.domain_layer.model.CurrencyEntity

class CurrencyMapper: Mapper<CurrencyEntity, Currency> {
    override fun mapFromEntity(type: CurrencyEntity): Currency {
        return Currency(
            code = type.code,
            name = type.name,
            symbol = type.symbol
        )
    }

    override fun mapToEntity(type: Currency): CurrencyEntity {
        return CurrencyEntity(
            code = type.code,
            name = type.name,
            symbol = type.symbol
        )
    }
}
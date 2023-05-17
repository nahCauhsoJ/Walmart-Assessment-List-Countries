package com.sample.data_layer.mapper

import com.example.domain_layer.model.CountryResponseItem
import com.sample.data_layer.model.CountryResponseItemEntity

class CountryMapper(
    private val currencyMapper: CurrencyMapper,
    private val languageMapper: LanguageMapper
): Mapper<CountryResponseItemEntity,CountryResponseItem> {
    override fun mapFromEntity(type: CountryResponseItemEntity): CountryResponseItem {
        return CountryResponseItem(
            capital = type.capital,
            code = type.code,
            currency = currencyMapper.mapFromEntity(type.currency),
            demonym = type.demonym,
            flag = type.flag,
            language = languageMapper.mapFromEntity(type.language),
            name = type.name,
            region = type.region
        )
    }

    override fun mapToEntity(type: CountryResponseItem): CountryResponseItemEntity {
        return CountryResponseItemEntity(
            capital = type.capital,
            code = type.code,
            currency = currencyMapper.mapToEntity(type.currency),
            demonym = type.demonym,
            flag = type.flag,
            language = languageMapper.mapToEntity(type.language),
            name = type.name,
            region = type.region
        )
    }
}
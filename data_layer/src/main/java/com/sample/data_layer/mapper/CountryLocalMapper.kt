package com.sample.data_layer.mapper

import com.example.domain_layer.model.CurrencyEntity
import com.example.domain_layer.model.LanguageEntity
import com.sample.data_layer.model.CountryLocalItem
import com.sample.data_layer.model.CountryResponseItemEntity

class CountryLocalMapper: Mapper<CountryResponseItemEntity, CountryLocalItem> {
    override fun mapFromEntity(type: CountryResponseItemEntity) = 
        CountryLocalItem(
            capital = type.capital,
            code = type.code,
            currency = CurrencyEntity(
                code = type.currency.code,
                name = type.currency.name,
                symbol = type.currency.symbol
            ),
            demonym = type.demonym,
            flag = type.flag,
            language = LanguageEntity(
                code = type.language.code,
                iso6392 = type.language.iso6392,
                name = type.language.name,
                nativeName = type.language.nativeName
            ),
            name = type.name,
            region = type.region
        )

    override fun mapToEntity(type: CountryLocalItem) =
        CountryResponseItemEntity(
            capital = type.capital,
            code = type.code,
            currency = CurrencyEntity(
                code = type.currency.code,
                name = type.currency.name,
                symbol = type.currency.symbol
            ),
            demonym = type.demonym,
            flag = type.flag,
            language = LanguageEntity(
                code = type.language.code,
                iso6392 = type.language.iso6392,
                name = type.language.name,
                nativeName = type.language.nativeName
            ),
            name = type.name,
            region = type.region
        )
}
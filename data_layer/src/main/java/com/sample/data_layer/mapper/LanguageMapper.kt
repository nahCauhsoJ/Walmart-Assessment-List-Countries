package com.sample.data_layer.mapper

import com.example.domain_layer.model.Language
import com.example.domain_layer.model.LanguageEntity

class LanguageMapper: Mapper<LanguageEntity, Language> {
    override fun mapFromEntity(type: LanguageEntity): Language {
        return Language(
            code = type.code,
            iso6392 = type.iso6392,
            name = type.name,
            nativeName = type.nativeName
        )
    }

    override fun mapToEntity(type: Language): LanguageEntity {
        return LanguageEntity(
            code = type.code,
            iso6392 = type.iso6392,
            name = type.name,
            nativeName = type.nativeName
        )
    }
}
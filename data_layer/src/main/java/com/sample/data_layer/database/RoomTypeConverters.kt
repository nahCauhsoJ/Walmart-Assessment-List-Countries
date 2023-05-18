package com.sample.data_layer.database

import androidx.room.TypeConverter
import com.example.domain_layer.model.CurrencyEntity
import com.example.domain_layer.model.LanguageEntity
import com.google.gson.Gson

class RoomTypeConverters {
    @TypeConverter
    fun currencyToJson(data: CurrencyEntity) = Gson().toJson(data)

    @TypeConverter
    fun currencyFromJson(json: String) = Gson().fromJson(json, CurrencyEntity::class.java)

    @TypeConverter
    fun languageToJson(data: LanguageEntity) = Gson().toJson(data)

    @TypeConverter
    fun languageFromJson(json: String) = Gson().fromJson(json, LanguageEntity::class.java)
}
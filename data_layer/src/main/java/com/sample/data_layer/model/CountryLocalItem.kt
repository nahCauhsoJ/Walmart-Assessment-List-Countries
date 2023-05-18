package com.sample.data_layer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain_layer.model.CurrencyEntity
import com.example.domain_layer.model.LanguageEntity
import com.sample.data_layer.model.CountryLocalItem.Companion.tableName

@Entity(tableName = tableName)
data class CountryLocalItem(
    val capital: String,
    val code: String,
    val currency: CurrencyEntity,
    val demonym: String?,
    val flag: String,
    val language: LanguageEntity,
    @PrimaryKey val name: String,
    val region: String
) {
    companion object {
        const val tableName = "countries"
    }
}

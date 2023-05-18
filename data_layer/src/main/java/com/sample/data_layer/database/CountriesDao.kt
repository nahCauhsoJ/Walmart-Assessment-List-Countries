package com.sample.data_layer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sample.data_layer.model.CountryLocalItem

@Dao
interface CountriesDao {
    @Insert
    fun cacheCountries(data: List<CountryLocalItem>)

    @Query("DELETE FROM ${CountryLocalItem.tableName}")
    fun clearTable()

    @Query("SELECT * FROM ${CountryLocalItem.tableName}")
    suspend fun getAllCountries(): List<CountryLocalItem>
}
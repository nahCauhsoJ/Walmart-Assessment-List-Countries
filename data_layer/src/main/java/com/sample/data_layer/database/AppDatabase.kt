package com.sample.data_layer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sample.data_layer.model.CountryLocalItem

@Database(entities = [CountryLocalItem::class], version = 1)
@TypeConverters(value = [RoomTypeConverters::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun countriesDao(): CountriesDao

    companion object {
        const val dbName = "countries"
    }
}
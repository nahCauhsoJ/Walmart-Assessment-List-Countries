package com.sample.data_layer.repository

import com.sample.data_layer.api.CountryApiService
import com.example.domain_layer.repository.CountriesRepository
import com.example.domain_layer.util.ResponseState
import com.sample.data_layer.database.CountriesDao
import com.sample.data_layer.mapper.CountryLocalMapper
import com.sample.data_layer.mapper.CountryMapper
import com.sample.data_layer.model.CountryResponseItemEntity

class CountriesRepositoryImpl(
    private val api: CountryApiService,
    private val countriesDao: CountriesDao,
    private val countryMapper: CountryMapper,
    private val countryLocalMapper: CountryLocalMapper
): CountriesRepository {
    override suspend fun getCountries() = with(countriesDao.getAllCountries()) {
        if (!isEmpty()) ResponseState.Success(
            map{countryLocalMapper.mapToEntity(it)}
            .map{countryMapper.mapFromEntity(it)}
        ) else {
            try {
                api.getCountries().run{
                    if (isSuccessful) {
                        if (body() != null) {
                            storeCountries(body()!!)
                            ResponseState.Success(body()!!.map {
                                countryMapper.mapFromEntity(it)
                            })
                        } else ResponseState.Error("Unknown Error")
                    } else {
                        ResponseState.Error(errorBody()?.string() ?: "Unknown Error")
                    }
                }
            } catch (e: Exception) {
                // At this point, the most likely error is not having connection to internet.
                //      Check the log if it is something else, but we only need to show
                //      network error to users for now.
                println(e)
                ResponseState.NetworkError
            }
        }
    }

    private fun storeCountries(data: List<CountryResponseItemEntity>) {
        countriesDao.clearTable() // Just to make sure it's fresh.
        countriesDao.cacheCountries(
            data.map { countryLocalMapper.mapFromEntity(it) }
        )
    }
}
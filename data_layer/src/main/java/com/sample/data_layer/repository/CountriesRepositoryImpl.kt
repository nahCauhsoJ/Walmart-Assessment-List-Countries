package com.sample.data_layer.repository

import com.sample.data_layer.api.CountryApiService
import com.example.domain_layer.repository.CountriesRepository
import com.example.domain_layer.util.ResponseState
import com.sample.data_layer.mapper.CountryMapper

class CountriesRepositoryImpl(
    private val api: CountryApiService,
    private val countryMapper: CountryMapper
): CountriesRepository {
    override suspend fun getCountries() = try {
        api.getCountries().run{
            if (isSuccessful) {
                if (body() != null) ResponseState.Success(body()!!.map {
                    countryMapper.mapFromEntity(it)
                })
                else ResponseState.Error("Unknown Error")
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
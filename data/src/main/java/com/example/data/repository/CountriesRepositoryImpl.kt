package com.example.data.repository

import com.example.data.api.CountryApiService
import com.example.domain.repository.CountriesRepository
import com.example.domain.util.ResponseState

class CountriesRepositoryImpl(
    private val api: CountryApiService
): CountriesRepository {
    override suspend fun getCountries() = try {
        api.getCountries().run{
            if (isSuccessful) {
                if (body() != null) ResponseState.Success(body()!!)
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
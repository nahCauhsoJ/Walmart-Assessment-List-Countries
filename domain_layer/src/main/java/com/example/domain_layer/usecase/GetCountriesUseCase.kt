package com.example.domain_layer.usecase

import com.example.domain_layer.model.CountryResponseItem
import com.example.domain_layer.repository.CountriesRepository
import com.example.domain_layer.util.ResponseState

class GetCountriesUseCase(
    private val repository: CountriesRepository
) {
    suspend fun getAllCountries(): ResponseState<List<CountryResponseItem>> {
        return repository.getCountries()
    }
}
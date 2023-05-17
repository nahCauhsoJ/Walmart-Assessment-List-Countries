package com.example.domain.usecase

import com.example.domain.model.CountryResponseItem
import com.example.domain.repository.CountriesRepository
import com.example.domain.util.ResponseState

class GetCountriesUseCase(
    private val repository: CountriesRepository
) {
    suspend fun getAllCountries(): ResponseState<List<CountryResponseItem>> {
        return repository.getCountries()
    }
}
package com.example.walmartassessmentlistcountries.domain

import com.example.walmartassessmentlistcountries.data.dto.CountryResponseItem
import com.example.walmartassessmentlistcountries.data.repository.CountriesRepository
import com.example.walmartassessmentlistcountries.util.ResponseState

class GetCountriesUseCase(
    private val repository: CountriesRepository
) {
    suspend fun getAllCountries(): ResponseState<List<CountryResponseItem>> {
        return repository.getCountries()
    }
}
package com.example.walmartassessmentlistcountries.domain

import com.example.walmartassessmentlistcountries.data.dto.CountryResponse
import com.example.walmartassessmentlistcountries.data.repository.CountriesRepository
import com.example.walmartassessmentlistcountries.util.ResponseState

class GetCountriesUseCase(
    private val repository: CountriesRepository
) {
    suspend fun getAllCountries(): ResponseState<CountryResponse> {
        return repository.getCountries()
    }
}
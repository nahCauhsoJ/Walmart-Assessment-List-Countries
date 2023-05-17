package com.example.domain.repository

import com.example.domain.model.CountryResponseItem
import com.example.domain.util.ResponseState

interface CountriesRepository {
    suspend fun getCountries(): ResponseState<List<CountryResponseItem>>
}
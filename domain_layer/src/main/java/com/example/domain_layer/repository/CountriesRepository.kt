package com.example.domain_layer.repository

import com.example.domain_layer.model.CountryResponseItem
import com.example.domain_layer.util.ResponseState

interface CountriesRepository {
    suspend fun getCountries(): ResponseState<List<CountryResponseItem>>
}
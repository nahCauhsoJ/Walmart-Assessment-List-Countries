package com.example.walmartassessmentlistcountries.data.repository

import com.example.walmartassessmentlistcountries.data.dto.CountryResponse
import com.example.walmartassessmentlistcountries.util.ResponseState

interface CountriesRepository {
    suspend fun getCountries(): ResponseState<CountryResponse>
}
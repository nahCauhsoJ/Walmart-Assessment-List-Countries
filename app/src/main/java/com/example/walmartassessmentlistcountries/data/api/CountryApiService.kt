package com.example.walmartassessmentlistcountries.data.api

import com.example.walmartassessmentlistcountries.data.dto.CountryResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface CountryApiService {
    @GET(Constants.END_POINT_COUNTRY)
    suspend fun getCountries(): Response<List<CountryResponseItem>>
}
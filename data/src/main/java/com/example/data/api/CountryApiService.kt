package com.example.data.api

import com.example.domain.model.CountryResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface CountryApiService {
    @GET(Constants.END_POINT_COUNTRY)
    suspend fun getCountries(): Response<List<CountryResponseItem>>
}
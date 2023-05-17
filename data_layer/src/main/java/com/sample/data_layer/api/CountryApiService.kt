package com.sample.data_layer.api

import com.sample.data_layer.model.CountryResponseItemEntity
import retrofit2.Response
import retrofit2.http.GET

interface CountryApiService {
    @GET(Constants.END_POINT_COUNTRY)
    suspend fun getCountries(): Response<List<CountryResponseItemEntity>>
}
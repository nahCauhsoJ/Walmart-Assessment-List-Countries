package com.example.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {
    val instanceCountry = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_COUNTRY)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create<CountryApiService>()
}
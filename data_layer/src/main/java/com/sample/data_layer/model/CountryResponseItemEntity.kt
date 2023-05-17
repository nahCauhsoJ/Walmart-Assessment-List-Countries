package com.sample.data_layer.model


import com.example.domain_layer.model.CurrencyEntity
import com.example.domain_layer.model.LanguageEntity
import com.google.gson.annotations.SerializedName

data class CountryResponseItemEntity(
    @SerializedName("capital")
    val capital: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("currency")
    val currency: CurrencyEntity,
    @SerializedName("demonym")
    val demonym: String?,
    @SerializedName("flag")
    val flag: String,
    @SerializedName("language")
    val language: LanguageEntity,
    @SerializedName("name")
    val name: String,
    @SerializedName("region")
    val region: String
)
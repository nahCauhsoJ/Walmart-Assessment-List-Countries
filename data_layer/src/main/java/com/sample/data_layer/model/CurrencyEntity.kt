package com.example.domain_layer.model


import com.google.gson.annotations.SerializedName

data class CurrencyEntity(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String?
)
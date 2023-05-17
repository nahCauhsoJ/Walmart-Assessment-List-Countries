package com.example.domain_layer.model


import com.google.gson.annotations.SerializedName

data class LanguageEntity(
    @SerializedName("code")
    val code: String?,
    @SerializedName("iso639_2")
    val iso6392: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("nativeName")
    val nativeName: String?
)
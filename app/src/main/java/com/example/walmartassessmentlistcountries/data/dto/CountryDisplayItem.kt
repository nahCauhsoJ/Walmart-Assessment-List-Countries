package com.example.walmartassessmentlistcountries.data.dto

sealed class CountryDisplayItem {
    data class Header(val alphabet: String): CountryDisplayItem()
    data class Item(val data: CountryResponseItem): CountryDisplayItem()
}
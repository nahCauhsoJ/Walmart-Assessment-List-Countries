package com.example.walmartassessmentlistcountries.entity

import com.example.domain.model.CountryResponseItem

sealed class CountryDisplayItem {
    data class Header(val alphabet: String) : CountryDisplayItem()
    data class Item(val data: CountryResponseItem) : CountryDisplayItem()
}
package com.example.walmartassessmentlistcountries.entity

import com.sample.data_layer.model.CountryResponseItemEntity

sealed class CountryDisplayItem {
    data class Header(val alphabet: String) : CountryDisplayItem()
    data class Item(val data: CountryResponseItemEntity) : CountryDisplayItem()
}
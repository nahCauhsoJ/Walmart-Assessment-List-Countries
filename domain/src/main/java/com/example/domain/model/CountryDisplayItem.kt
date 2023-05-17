package com.example.domain.model


sealed class CountryDisplayItem {
    data class Header(val alphabet: String) : CountryDisplayItem()
    data class Item(val data: CountryResponseItem) : CountryDisplayItem()
}
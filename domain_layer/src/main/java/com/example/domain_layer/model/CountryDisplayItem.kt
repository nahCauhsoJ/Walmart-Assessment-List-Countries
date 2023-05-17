package com.example.domain_layer.model


sealed class CountryDisplayItem {
    data class Header(val alphabet: String) : CountryDisplayItem()
    data class Item(val data: CountryResponseItem) : CountryDisplayItem()
}
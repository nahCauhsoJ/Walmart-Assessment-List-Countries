package com.example.walmartassessmentlistcountries.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.walmartassessmentlistcountries.R
import com.example.domain_layer.model.CountryDisplayItem
import com.example.walmartassessmentlistcountries.databinding.CountryHeaderItemBinding
import com.example.walmartassessmentlistcountries.databinding.CountryItemBinding

class CountryViewHolder(
    private val binding: CountryItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(country: CountryDisplayItem.Item) {
        val data = country.data
        with (binding) {
            cardCountryName.text = root.resources.getString(
                R.string.countryFullName, data.name, data.region
            )
            cardCountryCode.text = data.code
            cardCountryCapital.text = data.capital
        }
    }
}

class CountryHeaderViewHolder(
    private val binding: CountryHeaderItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(alphabet: CountryDisplayItem.Header) {
        with (binding) {
            countryHeaderText.text = alphabet.alphabet
        }
    }
}
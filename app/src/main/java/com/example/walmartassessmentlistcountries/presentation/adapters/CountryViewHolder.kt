package com.example.walmartassessmentlistcountries.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.walmartassessmentlistcountries.R
import com.example.walmartassessmentlistcountries.data.dto.CountryResponseItem
import com.example.walmartassessmentlistcountries.databinding.CountryItemBinding

class CountryViewHolder(
    private val binding: CountryItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(country: CountryResponseItem) {
        with (binding) {
            cardCountryName.text = root.resources.getString(
                R.string.countryFullName, country.name, country.region
            )
            cardCountryCode.text = country.code
            cardCountryCapital.text = country.capital
        }
    }
}
package com.example.walmartassessmentlistcountries.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.walmartassessmentlistcountries.data.dto.CountryResponseItem
import com.example.walmartassessmentlistcountries.databinding.CountryItemBinding

class CountryAdapter(private val countryList: List<CountryResponseItem>) :
    RecyclerView.Adapter<CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryViewHolder(
        CountryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) =
        holder.bind(countryList[position])

    override fun getItemCount() = countryList.size
}
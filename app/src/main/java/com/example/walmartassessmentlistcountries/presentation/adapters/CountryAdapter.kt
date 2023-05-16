package com.example.walmartassessmentlistcountries.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.walmartassessmentlistcountries.data.dto.CountryDisplayItem
import com.example.walmartassessmentlistcountries.databinding.CountryHeaderItemBinding
import com.example.walmartassessmentlistcountries.databinding.CountryItemBinding

class CountryAdapter(private val countryList: List<CountryDisplayItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var countryListFiltered: MutableList<CountryDisplayItem> = countryList.toMutableList()

    fun filterItems(input: String?) {
        countryListFiltered.clear()
        countryListFiltered.addAll(countryList.filter {
            if (input != null) {
                if (it is CountryDisplayItem.Item) {
                    it.data.name.lowercase().contains(input.lowercase())
                } else true
            }
            else true
        })
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (countryListFiltered[position] is CountryDisplayItem.Item) 0
        else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == 0) (
            CountryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ).run{ CountryViewHolder(this) }
        ) else CountryHeaderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).run { CountryHeaderViewHolder(this) }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        if (getItemViewType(position) == 0) {
            holder as CountryViewHolder
            holder.bind(countryListFiltered[position] as CountryDisplayItem.Item)
        } else {
            holder as CountryHeaderViewHolder
            holder.bind(countryListFiltered[position] as CountryDisplayItem.Header)
        }

    override fun getItemCount() = countryListFiltered.size
}
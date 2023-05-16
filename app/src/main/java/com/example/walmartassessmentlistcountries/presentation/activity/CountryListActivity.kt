package com.example.walmartassessmentlistcountries.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walmartassessmentlistcountries.R
import com.example.walmartassessmentlistcountries.data.api.RetrofitClient
import com.example.walmartassessmentlistcountries.data.dto.CountryResponseItem
import com.example.walmartassessmentlistcountries.data.repository.CountriesRepositoryImpl
import com.example.walmartassessmentlistcountries.databinding.ActivityCountryListBinding
import com.example.walmartassessmentlistcountries.domain.GetCountriesUseCase
import com.example.walmartassessmentlistcountries.presentation.adapters.CountryAdapter
import com.example.walmartassessmentlistcountries.presentation.adapters.CountryViewHolder
import com.example.walmartassessmentlistcountries.presentation.viewmodel.CountriesViewModel
import com.example.walmartassessmentlistcountries.util.createFactory
import com.example.walmartassessmentlistcountries.util.showErrorSnackbar

class CountryListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCountryListBinding
    private lateinit var countriesViewModel: CountriesViewModel
    private val countries = mutableListOf<CountryResponseItem>()
    private lateinit var countriesAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        setupObserver()

        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with (binding.countryListView) {
            layoutManager = LinearLayoutManager(context)
            adapter = CountryAdapter(countries).also {
                countriesAdapter = it
            }
        }

        with (binding.countrySearchBar) {
            addTextChangedListener {
                countriesAdapter.filterItems(it?.toString())
            }
        }
    }

    private fun initViewModel() {
        val repository = CountriesRepositoryImpl(RetrofitClient.instanceCountry)
        val factory = CountriesViewModel(
            application = application,
            GetCountriesUseCase(repository)
        ).createFactory()
        countriesViewModel = ViewModelProvider(this, factory)[CountriesViewModel::class.java]
    }

    private fun setupObserver() {
        countriesViewModel.countryListLiveData.observe(this){
            val oldCount = countries.size
            countries.clear()
            binding.countryListView.adapter?.notifyItemRangeRemoved(0, oldCount)

            it?.let {
                countries.addAll(it)
                binding.countryListView.adapter?.notifyItemRangeRemoved(0, countries.size)
                countriesAdapter.filterItems(countriesViewModel.searchInput.value ?: "")
            }
        }

        countriesViewModel.isLoadingData.observe(this) {
            binding.countriesDataLoading.visibility = if (it) View.VISIBLE else View.GONE
        }

        countriesViewModel.errorMessage.observe(this) { error ->
            error?.let{ binding.root.showErrorSnackbar(it) }
        }

        countriesViewModel.isInternetAvailable.observe(this) { isInternetAvailable ->
            if (isInternetAvailable) {
                countriesViewModel.getCountries()
            } else {
                binding.root.showErrorSnackbar(resources.getString(R.string.internetUnavailable))
            }
        }
    }
}
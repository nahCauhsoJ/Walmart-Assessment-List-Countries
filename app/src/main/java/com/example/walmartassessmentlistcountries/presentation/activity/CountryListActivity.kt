package com.example.walmartassessmentlistcountries.presentation.activity

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain_layer.model.CountryDisplayItem
import com.example.domain_layer.usecase.GetCountriesUseCase
import com.example.domain_layer.util.ErrorBody
import com.example.walmartassessmentlistcountries.R
import com.example.walmartassessmentlistcountries.databinding.ActivityCountryListBinding
import com.example.walmartassessmentlistcountries.presentation.adapters.CountryAdapter
import com.example.walmartassessmentlistcountries.presentation.viewmodel.CountriesViewModel
import com.example.walmartassessmentlistcountries.util.createFactory
import com.example.walmartassessmentlistcountries.util.isInternetAvailable
import com.example.walmartassessmentlistcountries.util.showErrorSnackbar
import com.google.android.material.snackbar.Snackbar
import com.sample.data_layer.api.RetrofitClient
import com.sample.data_layer.mapper.CountryMapper
import com.sample.data_layer.mapper.CurrencyMapper
import com.sample.data_layer.mapper.LanguageMapper
import com.sample.data_layer.repository.CountriesRepositoryImpl


class CountryListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCountryListBinding
    private lateinit var countriesViewModel: CountriesViewModel
    private val countries = mutableListOf<CountryDisplayItem>()
    private lateinit var countriesAdapter: CountryAdapter

    private var networkSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        setupObserver()
        setupNetworkStatus()

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

        if (isInternetAvailable()) countriesViewModel.getCountries()
        else binding.root.showErrorSnackbar(resources.getString(R.string.internetUnavailable))
    }

    private fun initViewModel() {
        val repository =
            CountriesRepositoryImpl(
                RetrofitClient.instanceCountry,
                CountryMapper(
                    CurrencyMapper(),
                    LanguageMapper()
                )
            )
        val factory = CountriesViewModel(
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
            error?.let{
                val err = if (error is ErrorBody.StringResource)
                    resources.getString(error.id)
                else {
                    error as ErrorBody.Message
                    error.msg
                }
                binding.root.showErrorSnackbar(err)
            }
        }
    }

    private fun setupNetworkStatus() {
        countriesViewModel.networkManager.startCallback(
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        )

        countriesViewModel.networkAvailability.observe(this) {
            if (it) {
//                networkSnackbar?.dismiss()
//                networkSnackbar = null
            } else {
                openNetworkSnackbar()
            }
        }
    }

    private fun openNetworkSnackbar() {
        networkSnackbar = Snackbar.make(
            binding.root, "Internet unavailable", Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
                networkSnackbar?.dismiss()
                if (!isInternetAvailable()) openNetworkSnackbar()
                else countriesViewModel.getCountries()
            }
        networkSnackbar?.show()
    }
}
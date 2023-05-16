package com.example.walmartassessmentlistcountries.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.walmartassessmentlistcountries.R
import com.example.walmartassessmentlistcountries.data.dto.CountryResponse
import com.example.walmartassessmentlistcountries.data.dto.CountryResponseItem
import com.example.walmartassessmentlistcountries.domain.GetCountriesUseCase
import com.example.walmartassessmentlistcountries.util.ResponseState
import com.example.walmartassessmentlistcountries.util.isInternetAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CountriesViewModel(
    private val application: Application,
    private val getCountriesUseCase: GetCountriesUseCase
): AndroidViewModel(application) {
    private val _countryListLiveData = MutableLiveData<List<CountryResponseItem>>()
    val countryListLiveData: LiveData<List<CountryResponseItem>> = _countryListLiveData

    private val _countryListFilteredLiveData = MutableLiveData<List<CountryResponseItem>>()
    val countryListFilteredLiveData: LiveData<List<CountryResponseItem>> = _countryListFilteredLiveData

    private val _searchInput = MutableLiveData<String>("")
    val searchInput: LiveData<String> = _searchInput

    private val _isLoadingData = MutableLiveData(false)
    val isLoadingData: LiveData<Boolean> = _isLoadingData

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isInternetAvailable = MutableLiveData<Boolean>()
    val isInternetAvailable: LiveData<Boolean> = _isInternetAvailable

    private val resources = application.applicationContext.resources

    private var countryJob: Job? = null
    private var internetJob: Job? = null

    init{
        checkInternetConnectivity()
    }

    fun getCountries() {
        _isLoadingData.value = true
        countryJob = viewModelScope.launch(Dispatchers.IO) {
            getCountriesUseCase.getAllCountries().apply {
                when (this) {
                    is ResponseState.Success -> {
                        val response = body
                        _countryListLiveData.postValue(response.sortedBy { it.name })
                        //filterSearch(_searchInput.value ?: "")
                    }

                    is ResponseState.Error -> {
                        _errorMessage.postValue(errorBody)
                    }

                    is ResponseState.NetworkError -> {
                        _errorMessage.postValue(resources.getString(R.string.internetUnavailable))
                    }

                    else -> {}
                }

                _isLoadingData.postValue(false)
            }
        }
    }

    private fun checkInternetConnectivity() {
        internetJob = viewModelScope.launch(Dispatchers.IO) {
            val isInternetAvailable = application.applicationContext.isInternetAvailable()
            _isInternetAvailable.postValue(isInternetAvailable)
        }
    }

    override fun onCleared() {
        super.onCleared()
        countryJob?.cancel()
        internetJob?.cancel()
    }
}
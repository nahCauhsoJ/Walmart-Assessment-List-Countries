package com.example.walmartassessmentlistcountries.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.walmartassessmentlistcountries.R
import com.example.walmartassessmentlistcountries.data.dto.CountryResponse
import com.example.walmartassessmentlistcountries.domain.GetCountriesUseCase
import com.example.walmartassessmentlistcountries.util.ResponseState
import com.example.walmartassessmentlistcountries.util.isInternetAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountriesViewModel(
    private val application: Application,
    private val getCountriesUseCase: GetCountriesUseCase
): AndroidViewModel(application) {
    private val _countryListLiveData = MutableLiveData<CountryResponse>()
    val countryListLiveData: LiveData<CountryResponse> = _countryListLiveData

    private val _isLoadingData = MutableLiveData(false)
    val isLoadingData: LiveData<Boolean> = _isLoadingData

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isInternetAvailable = MutableLiveData<Boolean>()
    val isInternetAvailable: LiveData<Boolean> = _isInternetAvailable

    private val resources = application.applicationContext.resources

    init{
        checkInternetConnectivity()
    }

    fun getCountries() {
        _isLoadingData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            getCountriesUseCase.getAllCountries().apply {
                when (this) {
                    is ResponseState.Success -> {
                        val response = body
                        _countryListLiveData.postValue(response)
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
        viewModelScope.launch(Dispatchers.IO) {
            val isInternetAvailable = application.applicationContext.isInternetAvailable()
            _isInternetAvailable.postValue(isInternetAvailable)
        }
    }
}
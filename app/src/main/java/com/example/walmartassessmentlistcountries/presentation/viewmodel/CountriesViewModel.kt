package com.example.walmartassessmentlistcountries.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain_layer.model.CountryDisplayItem
import com.example.walmartassessmentlistcountries.R
import com.example.walmartassessmentlistcountries.util.toSealed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CountriesViewModel(
    private val getCountriesUseCase: com.example.domain_layer.usecase.GetCountriesUseCase
): ViewModel() {
    private val _countryListLiveData = MutableLiveData<List<CountryDisplayItem>>()
    val countryListLiveData: LiveData<List<CountryDisplayItem>> = _countryListLiveData

    private val _searchInput = MutableLiveData<String>("")
    val searchInput: LiveData<String> = _searchInput

    private val _isLoadingData = MutableLiveData(false)
    val isLoadingData: LiveData<Boolean> = _isLoadingData

    private val _errorMessage = MutableLiveData<com.example.domain_layer.util.ErrorBody>()
    val errorMessage: LiveData<com.example.domain_layer.util.ErrorBody> = _errorMessage

    private var countryJob: Job? = null

    fun getCountries() {
        _isLoadingData.value = true
        countryJob = viewModelScope.launch(Dispatchers.IO) {
            getCountriesUseCase.getAllCountries().apply {
                when (this) {
                    is com.example.domain_layer.util.ResponseState.Success -> {
                        val response = body
                        val justItemList = response.sortedBy { it.name }.toSealed()
                        val originalSize = justItemList.size

                        val occurredHeader = mutableSetOf<Char>()
                        var currentLetter = ""
                        for (i in 0 until originalSize) {
                            val reversedIndex = originalSize - i - 1
                            val item = justItemList[reversedIndex] as CountryDisplayItem.Item
                            val firstLetter = item.data.name[0]

                            if (!occurredHeader.contains(firstLetter)) {
                                occurredHeader.add(firstLetter)

                                if (currentLetter.isNotEmpty()) {
                                    justItemList.add(reversedIndex + 1, CountryDisplayItem.Header(
                                        currentLetter
                                    ))
                                }

                                currentLetter = firstLetter.toString()
                            }
                        }
                        justItemList.add(0, CountryDisplayItem.Header(currentLetter))

                        _countryListLiveData.postValue(justItemList.toList())
                    }

                    is com.example.domain_layer.util.ResponseState.Error -> {
                        _errorMessage.postValue(com.example.domain_layer.util.ErrorBody.Message(errorBody ?: ""))
                    }

                    is com.example.domain_layer.util.ResponseState.NetworkError -> {
                        _errorMessage.postValue(com.example.domain_layer.util.ErrorBody.StringResource(R.string.internetUnavailable))
                    }

                    else -> {}
                }

                _isLoadingData.postValue(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        countryJob?.cancel()
    }
}
package com.example.walmartassessmentlistcountries.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.data_layer.model.CountryResponseItemEntity
import com.example.domain_layer.model.CurrencyEntity
import com.example.domain_layer.model.LanguageEntity
import com.example.walmartassessmentlistcountries.presentation.viewmodel.CountriesViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CountriesViewModelTest {

    private val useCaseClass = mockk<com.example.domain_layer.usecase.GetCountriesUseCase>()
    private lateinit var vm: CountriesViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        vm = CountriesViewModel(useCaseClass)
    }

    @Test
    fun `GIVEN usecase WHEN empty THEN list is empty`() = runTest {
        coEvery{ useCaseClass.getAllCountries() } returns com.example.domain_layer.util.ResponseState.Success(
            listOf()
        )

        vm.getCountries()

        coVerify { useCaseClass.getAllCountries() }
        assert(vm.countryListLiveData.value!!.isEmpty())
    }

    @Test
    fun `GIVEN usecase WHEN one item THEN item matches`() = runTest {
        coEvery{ useCaseClass.getAllCountries() } returns com.example.domain_layer.util.ResponseState.Success(
            listOf(
                CountryResponseItemEntity(
                    "",
                    "",
                    CurrencyEntity(
                        "",
                        "",
                        null
                    ),
                    null,
                    "",
                    LanguageEntity(
                        null,
                        null,
                        "",
                        null
                    ),
                    "Earth",
                    ""
                )
            )
        )

        vm.getCountries()

        coVerify { useCaseClass.getAllCountries() }
        assert(vm.countryListLiveData.value!!.size == 1)
        //assert(vm.countryListLiveData.value!!.first().name == "Earth")
    }
}
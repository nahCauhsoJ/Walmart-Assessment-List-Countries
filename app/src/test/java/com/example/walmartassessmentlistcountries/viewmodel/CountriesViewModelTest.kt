package com.example.walmartassessmentlistcountries.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.CountryResponseItem
import com.example.domain.model.Currency
import com.example.domain.model.Language
import com.example.domain.usecase.GetCountriesUseCase
import com.example.domain.util.ResponseState
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

    private val useCaseClass = mockk<GetCountriesUseCase>()
    private lateinit var vm: CountriesViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        vm = CountriesViewModel(useCaseClass)
    }

    @Test
    fun `GIVEN usecase WHEN empty THEN list is empty`() = runTest {
        coEvery{ useCaseClass.getAllCountries() } returns ResponseState.Success(
            listOf()
        )

        vm.getCountries()

        coVerify { useCaseClass.getAllCountries() }
        assert(vm.countryListLiveData.value!!.isEmpty())
    }

    @Test
    fun `GIVEN usecase WHEN one item THEN item matches`() = runTest {
        coEvery{ useCaseClass.getAllCountries() } returns ResponseState.Success(
            listOf(
                CountryResponseItem(
                    "",
                    "",
                    Currency(
                        "",
                        "",
                        null
                    ),
                    null,
                    "",
                    Language(
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
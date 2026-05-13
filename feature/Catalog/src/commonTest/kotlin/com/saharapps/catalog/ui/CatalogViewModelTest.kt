package com.saharapps.catalog.ui

import com.saharapps.catalog.usecase.FakeDeleteCatalogUseCase
import com.saharapps.catalog.usecase.FakeGetCatalogUseCase
import com.saharapps.catalog.usecase.FakeSaveCatalogUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class CatalogViewModelTest {

    private lateinit var getCatalogUseCase: FakeGetCatalogUseCase
    private lateinit var saveCatalogUseCase: FakeSaveCatalogUseCase
    private lateinit var deleteCatalogUseCase: FakeDeleteCatalogUseCase

    private lateinit var viewModel: CatalogViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        getCatalogUseCase = FakeGetCatalogUseCase()
        saveCatalogUseCase = FakeSaveCatalogUseCase()
        deleteCatalogUseCase = FakeDeleteCatalogUseCase()

        viewModel = CatalogViewModel(
            getCatalogUseCase,
            saveCatalogUseCase,
            deleteCatalogUseCase
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
package com.saharapps.catalog.ui

import app.cash.turbine.test
import com.saharapps.catalog.CatalogItem
import com.saharapps.catalog.usecase.FakeDeleteCatalogUseCase
import com.saharapps.catalog.usecase.FakeGetCatalogUseCase
import com.saharapps.catalog.usecase.FakeSaveCatalogUseCase
import com.saharapps.ui.ViewStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
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

    @Test
    fun `getCatalogs should update state to SUCCESS when use case returns data`() = runTest {
        // Arrange
        val mockCatalogs = listOf(
            CatalogItem(
                id = 1,
                name = "Test Item",
                imagePath = ""
            )
        )

        getCatalogUseCase.result = Result.success(mockCatalogs)

        // Act & Assert
        viewModel.catalogUiState.test {
            assertEquals(ViewStatus.INITIAL, awaitItem().viewStatus)

            viewModel.getCatalogs()
            assertEquals(ViewStatus.LOADING, awaitItem().viewStatus)

            testDispatcher.scheduler.advanceTimeBy(501)
            val successState = awaitItem()
            assertEquals(ViewStatus.SUCCESS, successState.viewStatus)
            assertEquals(mockCatalogs, successState.catalogs)
        }
    }

    @Test
    fun `getCatalogs should update state to FAILED when use case fails`() = runTest {
        // ARRANGE
        val errorMessage = "Network Error"
        getCatalogUseCase.result = Result.failure(Exception(errorMessage))

        //ACT & ASSERT
        viewModel.catalogUiState.test {
            assertEquals(ViewStatus.INITIAL, awaitItem().viewStatus)

            viewModel.getCatalogs()
            assertEquals(ViewStatus.LOADING, awaitItem().viewStatus)

            testDispatcher.scheduler.advanceTimeBy(501)
            val failedState = awaitItem()
            assertEquals(ViewStatus.FAILED, failedState.viewStatus)
            assertEquals(errorMessage, failedState.failedMessage)
        }
    }
}
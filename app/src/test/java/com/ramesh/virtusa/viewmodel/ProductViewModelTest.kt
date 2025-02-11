package com.ramesh.virtusa.viewmodel
import com.ramesh.virtusa.domain.usecase.GetProductsByCategoryNameUseCase
import com.ramesh.virtusa.presentation.state.UIState
import com.ramesh.virtusa.presentation.viewmodel.GetProductsByCategoryNameViewModel
import com.ramesh.virtusa.utilities.DummyData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    private lateinit var viewModel: GetProductsByCategoryNameViewModel
    private lateinit var getProductsUseCase: GetProductsByCategoryNameUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getProductsUseCase = mock(GetProductsByCategoryNameUseCase::class.java)
        viewModel = GetProductsByCategoryNameViewModel(getProductsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllProducts success`() = runTest(testDispatcher) {
        // Given
        val productResponse = DummyData.createDummyProductResponseItem()
        val categoryName = "jewelery"
        `when`(getProductsUseCase.execute(categoryName)).thenReturn(flowOf(productResponse))

        // When
        viewModel.getProductsByCategoryName(categoryName)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val expectedState = UIState.Success(productResponse)
        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun `getAllProducts error`() = runTest(testDispatcher) {
        // Given
        val errorMessage = "Network error occurred"
        val categoryName = "jewelery"
        `when`(getProductsUseCase.execute(categoryName)).thenThrow(java.io.IOException(errorMessage))

        // When
        viewModel.getProductsByCategoryName(categoryName)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val expectedState = UIState.Error(errorMessage)
        assertEquals(expectedState, viewModel.uiState.value)
    }
}
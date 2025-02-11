package com.ramesh.virtusa.viewmodel

import com.ramesh.virtusa.domain.usecase.GetAllCategoryUseCase
import com.ramesh.virtusa.presentation.state.UIState
import com.ramesh.virtusa.presentation.viewmodel.GetAllCategoryViewModel
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
class GetAllCategoryViewModelTest {

    private lateinit var viewModel: GetAllCategoryViewModel
    private lateinit var getAllCategoryUseCase: GetAllCategoryUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getAllCategoryUseCase = mock(GetAllCategoryUseCase::class.java)
        viewModel = GetAllCategoryViewModel(getAllCategoryUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllCategories success`() = runTest {
        // Given
        val categories = listOf("Electronics", "Clothing", "Books")
        `when`(getAllCategoryUseCase.execute(Unit)).thenReturn(flowOf(categories))

        // When
        viewModel.getAllCategories()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val expectedState = UIState.Success(categories)
        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun `getAllCategories error`() = runTest {
        // Given
        val errorMessage = "Network error occurred"
        `when`(getAllCategoryUseCase.execute(Unit)).thenThrow(java.io.IOException(errorMessage))

        // When
        viewModel.getAllCategories()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val expectedState = UIState.Error(errorMessage)
        assertEquals(expectedState, viewModel.uiState.value)
    }
}
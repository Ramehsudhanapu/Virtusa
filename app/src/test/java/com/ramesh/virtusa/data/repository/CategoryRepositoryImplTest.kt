package com.ramesh.virtusa.data.repository

import com.ramesh.virtusa.data.network.remote.CategoryAPIService
import com.ramesh.virtusa.data.repositoryimpl.CategoryRepositoryImpl
import com.ramesh.virtusa.utilities.common.AppStrings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CategoryRepositoryImplTest {

    @Mock
    private lateinit var apiService: CategoryAPIService

    private lateinit var repository: CategoryRepositoryImpl

    @Before
    fun setUp() {
        repository = CategoryRepositoryImpl(apiService)
    }

    @Test
    fun `getAllCategoriesApiCall should emit categories on success`() = runTest {
        // Given
        val categories = listOf("Electronics", "Clothing", "Books")
        `when`(apiService.getAllCategories()).thenReturn(categories)

        // When
        val result = repository.getAllCategories().first()

        // Then
        assertEquals(categories, result)
    }

    @Test
    fun `getAllCategoriesApiCall should throw exception on error`() = runTest {
        // Given
        val errorMessage = AppStrings.UNABLE_TO_FETCH_PRODUCTS
        `when`(apiService.getAllCategories()).thenThrow(RuntimeException())

        // When
        var exception: Throwable? = null
        repository.getAllCategories()
            .catch { e -> exception = e }
            .collect {}

        // Then
        assertTrue(exception is Exception)
        assertEquals(errorMessage, exception?.message)
    }

}
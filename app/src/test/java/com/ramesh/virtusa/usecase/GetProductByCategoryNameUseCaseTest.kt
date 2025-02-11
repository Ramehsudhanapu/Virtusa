package com.ramesh.virtusa.usecase

import com.ramesh.virtusa.data.model.ProductResponse
import com.ramesh.virtusa.domain.repository.ProductRepository
import com.ramesh.virtusa.domain.usecase.GetProductsByCategoryNameUseCase
import com.ramesh.virtusa.utilities.DummyData
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetProductByCategoryNameUseCaseTest {

   @Mock
   private  lateinit var   productRepository: ProductRepository
   private  lateinit var useCase: GetProductsByCategoryNameUseCase
   @Before
   fun setUp()
   {
       useCase = GetProductsByCategoryNameUseCase(productRepository)

   }
    @Test
    fun `execute should return products by category name from repository`() = runTest {
        val categoryName = "jewelery"
        val productResponse = DummyData.createDummyProductResponseItem()
        given(productRepository.getAllProducts(categoryName)).willReturn(flowOf(productResponse))
// When
        val result = useCase.execute(categoryName).first()

        // Then
        assertEquals(productResponse, result)


    }



    @Test
    fun `execute should return error flow when exception occurred`() = runTest {
        // Given
        val categoryName = "jewelery"
        val expectException = RuntimeException("An error occurred")
        val errorFlow: Flow<List<ProductResponse>> = flow { throw expectException }
        given(productRepository.getAllProducts(categoryName)).willReturn(errorFlow)

        // When
        val actualException = runCatching {
            useCase.execute(categoryName).first()
        }

        // Then
        assertTrue(actualException.isFailure)
        assertEquals(expectException, actualException.exceptionOrNull())
    }




}
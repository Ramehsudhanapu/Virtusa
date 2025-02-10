package com.ramesh.virtusa.data.repositoryimpl

import com.ramesh.virtusa.data.model.Product
import com.ramesh.virtusa.data.model.ProductResponse
import com.ramesh.virtusa.data.network.remote.ProductDataAPIService
import com.ramesh.virtusa.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl  @Inject constructor(private val apiService: ProductDataAPIService) :
    ProductRepository {
    override suspend fun getAllProducts(categoryName: String): Flow<List<ProductResponse>>  = flow{
        try {
            emit(apiService.getAllProducts(categoryName).map { it })  // Replace with actual API call and data processing
        }catch( e:Exception ) {
            emit(listOf())  // Handle the error here
        }

    }

    override suspend fun getProductById(id: Int): Flow<Product>  = flow{

        try {
            emit(apiService.getProductById(id))

        } catch (E: Exception) {
            emit(Product())

        }
    }
}
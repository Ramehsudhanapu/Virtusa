package com.ramesh.virtusa.domain.repository
import com.ramesh.virtusa.data.model.Product
import com.ramesh.virtusa.data.model.ProductResponse
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllProducts(categoryName: String): Flow<List<ProductResponse>>
    suspend fun  getProductById(id:Int):Flow<Product>



}
package com.ramesh.virtusa.domain.usecase

import com.ramesh.virtusa.data.model.Product
import com.ramesh.virtusa.domain.repository.ProductRepository
import com.ramesh.virtusa.presentation.interactor.BaseUseCase

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsByIDUseCase @Inject constructor(private val productRepository: ProductRepository) :
    BaseUseCase<Int, Flow<Product>>() {
    override suspend fun execute(params: Int): Flow<Product> {
        return productRepository.getProductById(params)
    }
}
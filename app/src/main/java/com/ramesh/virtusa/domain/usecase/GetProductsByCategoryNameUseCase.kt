package com.ramesh.virtusa.domain.usecase

import com.ramesh.virtusa.data.model.ProductResponse
import com.ramesh.virtusa.domain.repository.ProductRepository
import com.ramesh.virtusa.presentation.interactor.BaseUseCase

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsByCategoryNameUseCase  @Inject constructor(private val productRepository:
                                                            ProductRepository
) : BaseUseCase<String, Flow<List<ProductResponse>>>()
{
    override suspend fun execute(params: String): Flow<List<ProductResponse>> {
        return productRepository.getAllProducts(params)

    }
}
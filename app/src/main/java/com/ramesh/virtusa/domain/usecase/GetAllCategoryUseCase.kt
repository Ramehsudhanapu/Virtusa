package com.ramesh.virtusa.domain.usecase

import com.ramesh.virtusa.domain.repository.CategoryRepository
import com.ramesh.virtusa.presentation.interactor.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategoryUseCase @Inject constructor(private val categoryRepository: CategoryRepository) :
    BaseUseCase<Unit, Flow<List<String>>>() {
    override suspend fun execute(params: Unit): Flow<List<String>>
    {
        return categoryRepository.getAllCategories()
    }

}


package com.ramesh.virtusa.data.repositoryimpl
import com.ramesh.virtusa.data.network.remote.CategoryAPIService
import com.ramesh.virtusa.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(private val apiService: CategoryAPIService) :
    CategoryRepository {
    override suspend fun getAllCategories(): Flow<List<String>> = flow {
        try {
             emit(apiService.getAllCategories().map { it })  // Replace with actual API call and data processing
        }catch( e:Exception ) {
            emit(listOf())  // Handle the error here
        }

    }




}
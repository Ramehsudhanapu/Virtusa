package com.ramesh.virtusa.di.repositoryBindingModule


import com.ramesh.virtusa.data.repositoryimpl.CategoryRepositoryImpl
import com.ramesh.virtusa.data.repositoryimpl.ProductRepositoryImpl
import com.ramesh.virtusa.domain.repository.CategoryRepository
import com.ramesh.virtusa.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract  class RepositoryModule {
    companion object {
        const val BASE_URL="https://fakestoreapi.com"
    }
    @Singleton
    @Binds
    abstract fun providCategoryRepository(repository: CategoryRepositoryImpl): CategoryRepository
    @Singleton
    @Binds
    abstract fun providProductRepository(repository: ProductRepositoryImpl): ProductRepository



}
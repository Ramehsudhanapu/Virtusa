package com.ramesh.virtusa.domain.repository

import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getAllCategories(): Flow<List<String>>
}
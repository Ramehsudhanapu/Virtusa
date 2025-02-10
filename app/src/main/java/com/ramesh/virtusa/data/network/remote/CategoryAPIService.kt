package com.ramesh.virtusa.data.network.remote

import retrofit2.http.GET

interface CategoryAPIService {
    @GET("products/categories")
    suspend fun getAllCategories(): List<String>

}
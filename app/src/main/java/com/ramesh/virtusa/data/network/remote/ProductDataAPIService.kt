package com.ramesh.virtusa.data.network.remote
import com.ramesh.virtusa.data.model.Product
import com.ramesh.virtusa.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDataAPIService {

    @GET("products/category/{categoryname}")
    suspend fun getAllProducts(@Path("categoryname") categoryname: String):
            List<ProductResponse>
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int):Product

}
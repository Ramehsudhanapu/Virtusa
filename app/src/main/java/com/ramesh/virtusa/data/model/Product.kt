package com.ramesh.virtusa.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("category")
    val category: String?=null,
    @SerializedName("description")
    val description: String?=null,
    @SerializedName("id")
    val id: Int?=null,
    @SerializedName("image")
    val image: String?=null,
    @SerializedName("price")
    val price: Double?=null,
    @SerializedName("title")
    val title: String?=null
)
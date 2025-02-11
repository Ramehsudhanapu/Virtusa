package com.ramesh.virtusa.utilities

import com.ramesh.virtusa.data.model.ProductResponse


object DummyData {
    fun createDummyProductResponse(
        id: Int? = null,
        title: String? = null,
        price: Double? = null,
        description: String? = null,
        category: String? = null,
        image: String? = null,
    ): ProductResponse {
        return ProductResponse(
            id = id,
            title = title,
            price = price,
            description = description,
            category = category,
            image = image,
        )
    }

    fun createDummyProductResponseItem(): List<ProductResponse> {
        val product1 = createDummyProductResponse(
            id = 5,
            title = "John Hardy Women's Legends Naga Gold & Silver Dragon Station Chain Bracelet",
            price = 695.0,
            description = "From our Legends Collection, the Naga was inspired by the mythical water dragon that protects the ocean's pearl. Wear facing inward to be bestowed with love and abundance, or outward for protection.",
            category = "jewelery",
            image = "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg",
        )

        val product2 = createDummyProductResponse(
            id = 6,
            title = "Solid Gold Petite Micropave ",
            price = 168.0,
            description = "Satisfaction Guaranteed. Return or exchange any order within 30 days.Designed and sold by Hafeez Center in the United States. Satisfaction Guaranteed. Return or exchange any order within 30 days.",
            category = "jewelery",
            image = "https://fakestoreapi.com/img/61sbMiUnoGL._AC_UL640_QL65_ML3_.jpg",
        )

        val product3 = createDummyProductResponse(
            id = 7,
            title = "White Gold Plated Princess",
            price = 9.99,
            description = "Classic Created Wedding Engagement Solitaire Diamond Promise Ring for Her. Gifts to spoil your love more for Engagement, Wedding, Anniversary, Valentine's Day...",
            category = "jewelery",
            image = "https://fakestoreapi.com/img/71YAIFU48IL._AC_UL640_QL65_ML3_.jpg",
        )

        val product4 = createDummyProductResponse(
            id = 8,
            title = "Pierced Owl Rose Gold Plated Stainless Steel Double",
            price = 10.99,
            description = "Rose Gold Plated Double Flared Tunnel Plug Earrings. Made of 316L Stainless Steel",
            category = "jewelery",
            image = "https://fakestoreapi.com/img/51UDEzMJVpL._AC_UL640_QL65_ML3_.jpg",
        )

        return listOf(product1, product2, product3, product4)
    }




}
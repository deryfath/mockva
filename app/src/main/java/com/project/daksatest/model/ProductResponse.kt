package com.project.daksatest.model


data class Products(
        val listProduct : List<ProductResponse>
)

data class ProductResponse(
    val id: Int,
    val image: String,
    val name: String,
    val price: Int
)

data class ProductRequest(
        val image: String,
        val name: String,
        val price: Int
)
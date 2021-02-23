package br.com.arquitetoandroid.appcommerce.model

import java.io.Serializable

data class Product(
    val id: String,
    val title: String,
    val category: ProductCategory,
    var description: String,
    var price: Double,
    var colors: List<ProductColor>,
    var sizes: List<ProductSize>,
    var images: List<ProductImage>): Serializable
package br.com.arquitetoandroid.appcommerce.model

import java.io.Serializable

data class Order(
    val id: String,
    val time: Long,
    val status: Status,
    val method: Method,
    val user: User,
    val products: MutableList<OrderedProduct> = emptyList<OrderedProduct>() as MutableList<OrderedProduct>,
    val price: Double = products.sumByDouble { it.quantity * it.product.price }): Serializable
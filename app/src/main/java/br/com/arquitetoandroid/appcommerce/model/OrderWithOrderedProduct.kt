package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Embedded
import androidx.room.Relation

data class OrderWithOrderedProduct(

    @Embedded val order: Order,

    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val products: MutableList<OrderedProduct> = emptyList<OrderedProduct>().toMutableList()
)

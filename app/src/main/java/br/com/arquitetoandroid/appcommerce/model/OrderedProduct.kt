package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "ordered_product_odp", primaryKeys = ["orderedId", "orderId"])
data class OrderedProduct(

    val orderedId: String = UUID.randomUUID().toString(),
    var orderId: String,
    @Embedded val product: Product,
    var quantity: Int): Serializable
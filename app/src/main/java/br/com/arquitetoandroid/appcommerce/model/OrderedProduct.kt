package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "ordered_product_odp", primaryKeys = ["orderedProductId", "orderId"])
data class OrderedProduct(

    var orderedProductId: String = UUID.randomUUID().toString(),
    var orderId: String,
    @Embedded val product: Product,
    var size: String = "",
    var color: String = "",
    var quantity: Int = 0): Serializable {

    @Ignore constructor(): this(UUID.randomUUID().toString(), "", Product(), "", "", 0)

}
package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "product_size_pds")
data class ProductSize(

    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var productId: String,
    val size: String,
    @Ignore var checked: Boolean = false): Serializable {

    constructor(id: String, productId: String, size: String): this(id, productId, size, false)
    @Ignore constructor(): this("", "", "", false)

}
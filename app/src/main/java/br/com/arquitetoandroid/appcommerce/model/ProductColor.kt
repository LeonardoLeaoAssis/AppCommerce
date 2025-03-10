package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "product_color_pdc")
data class ProductColor(

    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var productId: String,
    var name: String,
    var code: String,
    @Ignore var checked: Boolean = false): Serializable {

    constructor(id: String, productId: String, name: String, code: String): this(id, productId, name, code, false)
    @Ignore constructor(): this("", "", "", "", false)

}
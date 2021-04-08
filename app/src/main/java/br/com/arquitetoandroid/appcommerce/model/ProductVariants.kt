package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import java.io.Serializable

data class ProductVariants(

    @Embedded var product: Product,

    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    var colors: List<ProductColor> = emptyList(),

    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    var sizes: List<ProductSize> = emptyList(),

    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    var images: List<ProductImage> = emptyList()): Serializable {

    @Ignore constructor(): this(Product(), mutableListOf(), mutableListOf(), mutableListOf())

}
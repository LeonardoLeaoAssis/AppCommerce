package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "PRODUCT_IMAGE")
data class ProductImage(

    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var productId: String,
    var path: String): Serializable
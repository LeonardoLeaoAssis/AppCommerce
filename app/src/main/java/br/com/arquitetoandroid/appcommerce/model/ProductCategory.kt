package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "PRODUCT_CATEGORY")
data class ProductCategory(

    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var title: String,
    var featured: Boolean): Serializable
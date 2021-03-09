package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.arquitetoandroid.appcommerce.model.enums.Method
import br.com.arquitetoandroid.appcommerce.model.enums.Status
import java.io.Serializable
import java.util.*

@Entity(tableName = "order_ord")
data class Order(

    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var time: Long,
    var status: Status,
    var method: Method,
    var userId: String,
    var price: Double = 0.0): Serializable
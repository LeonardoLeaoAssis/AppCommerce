package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import br.com.arquitetoandroid.appcommerce.model.enums.Method
import br.com.arquitetoandroid.appcommerce.model.enums.Status
import java.io.Serializable
import java.util.*

@Entity(tableName = "order_ord")
data class Order(

    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var time: Long,
    var status: Status,
    var method: Method,
    var userId: String,
    var price: Double = 0.0): Serializable {

    @Ignore constructor(): this(UUID.randomUUID().toString(), System.currentTimeMillis(), Status.CART, Method.NONE, "", 0.0)

}
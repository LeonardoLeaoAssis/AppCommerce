package br.com.arquitetoandroid.appcommerce.database.dao

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import br.com.arquitetoandroid.appcommerce.model.Order
import br.com.arquitetoandroid.appcommerce.model.OrderWithOrderedProduct

interface IOrderDao {

    @Transaction
    @Query("SELECT * FROM order_ord WHERE id = :orderId")
    fun loadOrderAndProductsById(orderId: String): List<OrderWithOrderedProduct>

    @Query("SELECT * FROM order_ord WHERE userId = :userId")
    fun loadOrderByUser(userId: String): List<Order>

    @Transaction
    @Query("SELECT * FROM order_ord WHERE userId = :userId")
    fun loadOrderAndProductsByUser(userId: String): List<OrderWithOrderedProduct>

    @Insert
    fun insert(order: Order)

    @Insert
    fun insertAll(vararg order: Order)

    @Update
    fun update(order: Order)

}
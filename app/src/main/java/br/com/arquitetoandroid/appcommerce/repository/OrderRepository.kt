package br.com.arquitetoandroid.appcommerce.repository

import android.app.Application
import br.com.arquitetoandroid.appcommerce.database.AppDatabase

class OrderRepository(application: Application) {

    private val database = AppDatabase.getDatabase(application)

    private val orderDao = database.orderDao()

    fun loadAllOrderByUser(userId: String) = orderDao.loadAllOrderByUser(userId)

}
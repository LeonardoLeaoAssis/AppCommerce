package br.com.arquitetoandroid.appcommerce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.arquitetoandroid.appcommerce.model.OrderWithOrderedProduct
import br.com.arquitetoandroid.appcommerce.repository.OrderRepository

class OrderViewModel(application: Application): AndroidViewModel(application) {

    private val orderRepository = OrderRepository(application)

    fun getAllOrderByUser(userId: String) = orderRepository.loadAllOrderByUser(userId)
    fun place(fullOrder: OrderWithOrderedProduct) = orderRepository.place(fullOrder)

}
package br.com.arquitetoandroid.appcommerce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.arquitetoandroid.appcommerce.model.OrderWithOrderedProduct
import br.com.arquitetoandroid.appcommerce.model.UserWithAddress
import br.com.arquitetoandroid.appcommerce.repository.OrderRepository
import com.mercadopago.android.px.model.Payment

class OrderViewModel(application: Application): AndroidViewModel(application) {

    private val orderRepository = OrderRepository(application)

    fun getAllOrderByUser(userId: String) = orderRepository.loadAllOrderByUser(userId)
    fun place(user: UserWithAddress, fullOrder: OrderWithOrderedProduct) = orderRepository.place(user, fullOrder)
    fun save(fullOrder: OrderWithOrderedProduct, payment: Payment) = orderRepository.save(fullOrder, payment)

}
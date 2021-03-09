package br.com.arquitetoandroid.appcommerce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.arquitetoandroid.appcommerce.model.Order
import br.com.arquitetoandroid.appcommerce.model.OrderedProduct
import br.com.arquitetoandroid.appcommerce.model.Product
import br.com.arquitetoandroid.appcommerce.model.ProductVariants
import br.com.arquitetoandroid.appcommerce.model.enums.Method
import br.com.arquitetoandroid.appcommerce.model.enums.Status

class CartViewModel(application: Application): AndroidViewModel(application) {

    val cartPrice = MutableLiveData<Double>(CartViewModel.order.price)
    val orderedProducts = MutableLiveData<MutableList<OrderedProduct>>(CartViewModel.orderedProducts)

    companion object {

        val order = Order(
            time = System.currentTimeMillis(),
            status = Status.CART,
            method = Method.NONE,
            userId = "0"
        )

        private val orderedProducts = mutableListOf<OrderedProduct>()

        fun addProduct(product: ProductVariants, quantity: Int) {
            if (compare(product)) {
                updateQuantity(product.product, quantity)

                return
            }

            val orderProduct = OrderedProduct(
                orderId = order.id,
                product = product.product,
                quantity = quantity
            )

            product.colors.forEach {
                if (it.checked) {
                    orderProduct.color = it.name
                }
            }

            product.sizes.forEach {
                if (it.checked) {
                    orderProduct.size = it.size
                }
            }

            orderedProducts.add(orderProduct)
            updatePrice()
        }

        fun updateQuantity(product: Product, quantity: Int) {
            orderedProducts.forEach {
                if (it.product.id == product.id) {
                    if (quantity > 0) {
                        it.quantity = quantity
                    } else {
                        orderedProducts.remove(it)
                    }

                    updatePrice()

                    return
                }
            }
        }

        private fun compare(product: ProductVariants): Boolean {
            var validateColor: Boolean = false
            var validateSize = false

            orderedProducts.forEach { order ->
                if (order.product.id == product.product.id) {
                    product.colors.forEach {
                        if (it.checked) {
                            validateColor = order.color == it.name
                        }
                    }

                    product.sizes.forEach {
                        if (it.checked) {
                            validateSize = order.size == it.size
                        }
                    }

                    return validateColor && validateSize
                }
            }

            return false
        }

        private fun updatePrice() {
            order.price = orderedProducts.sumByDouble {
                it.quantity * it.product.price
            }
        }

    }

}
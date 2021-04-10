package br.com.arquitetoandroid.appcommerce.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.arquitetoandroid.appcommerce.model.Order
import br.com.arquitetoandroid.appcommerce.model.OrderWithOrderedProduct
import br.com.arquitetoandroid.appcommerce.viewmodel.CartViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class OrderRepository(application: Application) {

    private val firestore = FirebaseFirestore.getInstance();

    fun loadAllOrderByUser(userId: String): LiveData<List<Order>> {
        val liveData = MutableLiveData<List<Order>>()

        firestore.collection("user_usr").document(userId)
            .collection("order_ord")
            .orderBy("time", Query.Direction.DESCENDING)
            .addSnapshotListener { snap, e ->
                if (e != null) return@addSnapshotListener

                liveData.value = snap?.toObjects(Order::class.java)
            }

        return liveData
    }

    fun place(fullOrder: OrderWithOrderedProduct) {
        val userRef = firestore.collection("user_usr").document(fullOrder.order.userId)
        val orderRef = userRef.collection("order_ord").document(fullOrder.order.id)

        orderRef.set(fullOrder.order).addOnSuccessListener {
            fullOrder.products.forEach { product ->
                orderRef.collection("ordered_product_odp").document(product.orderedProductId).set(product)
            }

            CartViewModel.clear()
        }
    }

}
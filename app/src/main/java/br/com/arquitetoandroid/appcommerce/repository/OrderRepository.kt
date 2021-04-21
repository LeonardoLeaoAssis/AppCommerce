package br.com.arquitetoandroid.appcommerce.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.arquitetoandroid.appcommerce.model.Order
import br.com.arquitetoandroid.appcommerce.model.OrderWithOrderedProduct
import br.com.arquitetoandroid.appcommerce.model.UserWithAddress
import br.com.arquitetoandroid.appcommerce.model.enums.Method
import br.com.arquitetoandroid.appcommerce.model.enums.Status
import br.com.arquitetoandroid.appcommerce.viewmodel.CartViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mercadopago.android.px.model.Payment
import org.json.JSONArray
import org.json.JSONObject

class OrderRepository(application: Application) {

    companion object {
        private val TAG = OrderRepository::class.java.simpleName

        private const val BASE_URL = "https://api.mercadopago.com/checkout/preferences"
        private const val TOKEN = "?access_token=TEST-8076706258009250-042011-4ba076a05aa45d561a5cab66cf016b85-187614068"
    }

    private val firestore = FirebaseFirestore.getInstance()
    private val queue = Volley.newRequestQueue(application)

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

    fun place(user: UserWithAddress, fullOrder: OrderWithOrderedProduct): LiveData<String> {
        val liveData = MutableLiveData<String>()

        val params = JSONObject().also {
            val items = JSONArray()

            fullOrder.products.forEach { product ->
                JSONObject().also { item ->
                    item.put("id", product.product.id)
                    item.put("category_id", product.product.categoryId)
                    item.put("title", "${product.product.title} (${product.size}, ${product.color})")
                    item.put("quantity", product.quantity)
                    item.put("unit_price", product.product.price)

                    items.put(item)
                }
            }

            val payer = JSONObject().also { payer ->
                payer.put("name", user.user.name)
                payer.put("surname", user.user.surname)
                payer.put("email", user.user.email)

                JSONObject().also { address ->
                    val userAddress = user.addresses.first()

                    address.put("zip_code", userAddress.zipCode)
                    address.put("street_name", userAddress.addressLine1)
                    address.put("street_number", userAddress.number)

                    payer.put("address", address)
                }
            }

            it.put("items", items)
            it.put("payer", payer)
        }

        val request = JsonObjectRequest(Request.Method.POST,
            BASE_URL + TOKEN,
            params,
            { response ->
                val id = response.getString("id")

                liveData.value = id

                Log.e(TAG, id)
            },
            { error ->
                Log.e(TAG, error.message ?: "Error")
            })

        queue.add(request)

        return liveData
    }

    fun save(fullOrder: OrderWithOrderedProduct, payment: Payment) {
        fullOrder.order.method = Method.CREDIT_CARD
        fullOrder.order.status = Status.PAID

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
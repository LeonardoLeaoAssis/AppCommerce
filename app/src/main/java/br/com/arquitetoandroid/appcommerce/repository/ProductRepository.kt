package br.com.arquitetoandroid.appcommerce.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.arquitetoandroid.appcommerce.database.AppDatabase
import br.com.arquitetoandroid.appcommerce.model.Product
import br.com.arquitetoandroid.appcommerce.model.ProductCategory
import com.google.firebase.firestore.FirebaseFirestore

class ProductRepository(application: Application) {

    private val database = AppDatabase.getDatabase(application)
    private val firestore = FirebaseFirestore.getInstance()

    private val productDao = database.productDao()
    private val productCategoryDao = database.productCategoryDao()

    fun allCategories(): LiveData<List<ProductCategory>> {
        val liveData = MutableLiveData<List<ProductCategory>>()

        firestore.collection("product_category_pdc").addSnapshotListener { snap, e ->
            if (e != null) return@addSnapshotListener

            val list = mutableListOf<ProductCategory>()

            snap?.forEach {
                val productCategory = it.toObject(ProductCategory::class.java)
                productCategory.id = it.id

                list.add(productCategory)
            }

            liveData.value = list
        }

        return liveData
    }

    fun featuredCategories(): LiveData<List<ProductCategory>> {
        val liveData = MutableLiveData<List<ProductCategory>>()

        firestore.collection("product_category_pdc")
            .whereEqualTo("featured", true)
            .addSnapshotListener { snap, e ->
            if (e != null) return@addSnapshotListener

            val list = mutableListOf<ProductCategory>()

            snap?.forEach {
                val productCategory = it.toObject(ProductCategory::class.java)
                productCategory.id = it.id

                list.add(productCategory)
            }

            liveData.value = list
        }

        return liveData
    }

    fun featuredProducts(): LiveData<List<Product>> {
        val liveData = MutableLiveData<List<Product>>()

        firestore.collection("product_pdt")
            .whereEqualTo("featured", true)
            .addSnapshotListener { snap, e ->
                if (e != null) return@addSnapshotListener

                val list = mutableListOf<Product>()

                snap?.forEach {
                    val product = it.toObject(Product::class.java)
                    product.id = it.id

                    list.add(product)
                }

                liveData.value = list
            }

        return liveData
    }

    fun loadProductsByCategory(categoryId: String) = productDao.loadAllByCategory(categoryId)
    fun loadProductById(productId: String) = productDao.loadProductWithVariants(productId)

}
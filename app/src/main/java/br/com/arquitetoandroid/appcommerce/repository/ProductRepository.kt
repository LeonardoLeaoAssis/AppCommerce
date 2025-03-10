package br.com.arquitetoandroid.appcommerce.repository

import android.app.Application
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.arquitetoandroid.appcommerce.R
import br.com.arquitetoandroid.appcommerce.model.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.ktx.storage

class ProductRepository(application: Application) {

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = Firebase.storage(Firebase.app)
    private val glide = Glide.with(application)

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

    fun loadProductsByCategory(categoryId: String): LiveData<List<Product>> {
        val liveData = MutableLiveData<List<Product>>()

        firestore.collection("product_category_pdc")
            .document(categoryId)
            .get(Source.CACHE)
            .addOnSuccessListener { category ->
                val list = mutableListOf<Product>()

                if (category.get("products") != null) {
                    val productsRef = category.get("products") as List<DocumentReference>

                    productsRef.forEach{ doc ->
                        firestore.document(doc.path).get().addOnSuccessListener { doc ->
                            val product = doc.toObject(Product::class.java)
                            product?.id = doc.id

                            list.add(product!!)

                            liveData.value = list
                        }
                    }
                }
            }

        return liveData
    }

    fun loadProductById(productId: String): LiveData<ProductVariants> {
        val productVariantes = ProductVariants()
        val liveData = MutableLiveData<ProductVariants>(productVariantes)
        val productRef = firestore.collection("product_pdt").document(productId)

        productRef.get().addOnSuccessListener { snap ->
            productVariantes.apply {
                product = snap?.toObject(Product::class.java)!!
                product.id = snap.id

                liveData.value = this
            }
        }

        productRef.collection("product_color_pdc").get().addOnSuccessListener { colors ->
            productVariantes.colors = colors.toObjects(ProductColor::class.java)
            liveData.value = productVariantes
        }

        productRef.collection("product_size_pds").get().addOnSuccessListener { sizes ->
            productVariantes.sizes = sizes.toObjects(ProductSize::class.java)
            liveData.value = productVariantes
        }

        productRef.collection("product_image_pdi").get().addOnSuccessListener { images ->
            productVariantes.images = images.toObjects(ProductImage::class.java)
            liveData.value = productVariantes
        }

        return liveData
    }

    fun loadThumbnail(product: Product, imageView: ImageView) {
        storage.reference.child("products/${product.id}/${product.thumbnail}").downloadUrl.addOnSuccessListener {
            glide.load(it)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(imageView)
        }
    }

    fun loadImages(product: Product, path: String, imageView: ImageView) {
        storage.reference.child("products/${product.id}/$path").downloadUrl.addOnSuccessListener {
            glide.load(it)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(imageView)
        }
    }

}
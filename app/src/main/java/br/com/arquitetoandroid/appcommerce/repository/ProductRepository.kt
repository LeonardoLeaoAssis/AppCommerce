package br.com.arquitetoandroid.appcommerce.repository

import android.app.Application
import br.com.arquitetoandroid.appcommerce.database.AppDatabase
import br.com.arquitetoandroid.appcommerce.model.Product
import br.com.arquitetoandroid.appcommerce.model.ProductVariants

class ProductRepository(application: Application) {

    private val database = AppDatabase.getDatabase(application)

    private val productDao = database.productDao()
    private val productCategoryDao = database.productCategoryDao()

    val allCategories = productCategoryDao.loadAll()
    val featuredCategories = productCategoryDao.loadAllFeatured()
    val featuredProducts = productDao.loadAllFeatured()

    fun loadProductsByCategory(categoryId: String): List<Product> = productDao.loadAllByCategory(categoryId)
    fun loadProductById(productId: String): ProductVariants = productDao.loadProductWithVariants(productId)

}
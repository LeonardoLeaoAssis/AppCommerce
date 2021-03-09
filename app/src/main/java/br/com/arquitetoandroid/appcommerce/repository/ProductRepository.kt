package br.com.arquitetoandroid.appcommerce.repository

import android.app.Application
import br.com.arquitetoandroid.appcommerce.database.AppDatabase

class ProductRepository(application: Application) {

    private val database = AppDatabase.getDatabase(application)

    private val productDao = database.productDao()
    private val productCategoryDao = database.productCategoryDao()

    val allCategories = productCategoryDao.loadAll()
    val featuredCategories = productCategoryDao.loadAllFeatured()
    val featuredProducts = productDao.loadAllFeatured()

    fun loadProductsByCategory(categoryId: String) = productDao.loadAllByCategory(categoryId)
    fun loadProductById(productId: String) = productDao.loadProductWithVariants(productId)

}
package br.com.arquitetoandroid.appcommerce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.arquitetoandroid.appcommerce.repository.ProductRepository

class ProductViewModel(application: Application): AndroidViewModel(application) {

    private val productRepository = ProductRepository(application)

    val allCategories = productRepository.allCategories()
    val featuredCategories = productRepository.featuredCategories()
    val featuredProducts = productRepository.featuredProducts()

    fun getProductsByCategory(categoryId: String) = productRepository.loadProductsByCategory(categoryId)
    fun getProductWithVariantes(productId: String) = productRepository.loadProductById(productId)

}
package br.com.arquitetoandroid.appcommerce.interfaces

import br.com.arquitetoandroid.appcommerce.model.ProductCategory

interface ProductCategoryCallback {

    fun itemSelected(category: ProductCategory)

}
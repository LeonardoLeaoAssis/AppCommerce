package br.com.arquitetoandroid.appcommerce.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.arquitetoandroid.appcommerce.model.Product
import br.com.arquitetoandroid.appcommerce.model.ProductVariants

@Dao
interface IProductDao {

    @Query("SELECT * FROM product_pdt WHERE categoryId = :categoryId")
    fun loadAllByCategory(categoryId: String): LiveData<List<Product>>

    @Query("SELECT * FROM product_pdt WHERE featured = 1")
    fun loadAllFeatured(): LiveData<List<Product>>

    @Transaction
    @Query("SELECT * FROM product_pdt WHERE id = :productId")
    fun loadProductWithVariants(productId: String): LiveData<ProductVariants>

    @Insert
    fun insert(product: Product)

    @Update
    fun update(product: Product)

}
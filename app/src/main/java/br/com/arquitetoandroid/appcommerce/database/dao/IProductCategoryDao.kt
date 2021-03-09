package br.com.arquitetoandroid.appcommerce.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.arquitetoandroid.appcommerce.model.CategoryWithProduct
import br.com.arquitetoandroid.appcommerce.model.ProductCategory

@Dao
interface IProductCategoryDao {

    @Query("SELECT * FROM product_category_pdc")
    fun loadAll(): LiveData<List<ProductCategory>>

    @Query("SELECT * FROM product_category_pdc WHERE featured = 1")
    fun loadAllFeatured(): LiveData<List<ProductCategory>>

    @Transaction
    @Query("SELECT * FROM product_category_pdc WHERE id = :categoryId")
    fun loadCategoryWithProductById(categoryId: String): LiveData<CategoryWithProduct>

    @Insert
    fun insert(productCategory: ProductCategory)

    @Update
    fun update(productCategory: ProductCategory)

}
package br.com.arquitetoandroid.appcommerce.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.arquitetoandroid.appcommerce.database.dao.IOrderDao
import br.com.arquitetoandroid.appcommerce.database.dao.IProductCategoryDao
import br.com.arquitetoandroid.appcommerce.database.dao.IProductDao
import br.com.arquitetoandroid.appcommerce.database.dao.IUserDao
import br.com.arquitetoandroid.appcommerce.model.*

@Database(entities = [Order::class, OrderedProduct::class, Product::class, ProductCategory::class,
                      ProductColor::class, ProductImage::class, ProductSize::class, User::class, UserAddress::class],
          version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun orderDao(): IOrderDao
    abstract fun productCategoryDao(): IProductCategoryDao
    abstract fun productDao(): IProductDao
    abstract fun userDao(): IUserDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Application): AppDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appcommerce_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .createFromAsset("appcommerce_database.db")
                    .build()

                INSTANCE = instance
                instance
            }
        }

    }
}
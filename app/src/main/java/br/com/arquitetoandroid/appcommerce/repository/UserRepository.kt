package br.com.arquitetoandroid.appcommerce.repository

import android.app.Application
import androidx.preference.PreferenceManager
import br.com.arquitetoandroid.appcommerce.database.AppDatabase
import br.com.arquitetoandroid.appcommerce.model.User
import br.com.arquitetoandroid.appcommerce.model.UserAddress

class UserRepository(val application: Application) {

    companion object {
        val USER_ID = "USER_ID"
    }

    private val database = AppDatabase.getDatabase(application)

    private val userDao = database.userDao()

    fun login(email: String, password: String) = userDao.login(email, password)
    fun loadWithAddresses(userId: String) = userDao.loadUserById(userId)
    fun insert(user: User) = userDao.insert(user)
    fun insert(userAddress: UserAddress) = userDao.insert(userAddress)
    fun update(user: User) = userDao.update(user)
    fun update(userAddress: UserAddress) = userDao.update(userAddress)

    fun setUserId(userId: String) {
        PreferenceManager.getDefaultSharedPreferences(application).let {
            it.edit().putString(USER_ID, userId).apply()
        }
    }

    fun getUserId(): String? = PreferenceManager.getDefaultSharedPreferences(application).let {
        return it.getString(USER_ID, "")
    }

    fun logout() = PreferenceManager.getDefaultSharedPreferences(application).let {
        it.edit().remove(USER_ID).apply()
    }

}
package br.com.arquitetoandroid.appcommerce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.arquitetoandroid.appcommerce.model.User
import br.com.arquitetoandroid.appcommerce.model.UserAddress
import br.com.arquitetoandroid.appcommerce.repository.UserRepository

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val userRepository = UserRepository(application)

    fun createUser(user: User) = userRepository.insert(user)
    fun updateUser(user: User) = userRepository.update(user)
    fun createAddress(userAddress: UserAddress) = userRepository.insert(userAddress)
    fun updateAddress(userAddress: UserAddress) = userRepository.update(userAddress)

    fun login(email: String, password: String): MutableLiveData<User> {
        return MutableLiveData(
            userRepository.login(email, password).also { user ->
                if (user != null) {
                    userRepository.setUserId(user.id)
                }
            }
        )
    }

    fun logout() = userRepository.logout()
    fun isLogged() = userRepository.loadWithAddresses(userRepository.getUserId()!!)

}
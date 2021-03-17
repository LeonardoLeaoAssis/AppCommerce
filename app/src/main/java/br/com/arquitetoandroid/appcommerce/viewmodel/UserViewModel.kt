package br.com.arquitetoandroid.appcommerce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.arquitetoandroid.appcommerce.model.User
import br.com.arquitetoandroid.appcommerce.model.UserAddress
import br.com.arquitetoandroid.appcommerce.repository.UserRepository

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val userRepository = UserRepository(application)

    fun createUser(user: User) = userRepository.createUser(user)
    fun login(email: String, password: String) = userRepository.login(email, password)
    fun updateUser(user: User) = userRepository.update(user)
    fun createAddress(userAddress: UserAddress) = userRepository.insert(userAddress)
    fun updateAddress(userAddress: UserAddress) = userRepository.update(userAddress)
    fun logout() = userRepository.logout()
    fun isLogged() = userRepository.loadWithAddresses(userRepository.getUserId()!!)

}
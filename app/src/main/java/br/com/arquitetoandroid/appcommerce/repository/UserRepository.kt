package br.com.arquitetoandroid.appcommerce.repository

import android.app.Application
import android.util.Log
import androidx.preference.PreferenceManager
import br.com.arquitetoandroid.appcommerce.database.AppDatabase
import br.com.arquitetoandroid.appcommerce.model.User
import br.com.arquitetoandroid.appcommerce.model.UserAddress
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONObject

class UserRepository(val application: Application) {

    companion object {
        private val TAG = UserRepository::class.java.simpleName

        private val USER_ID = "USER_ID"

        const val BASE_URL = "https://identitytoolkit.googleapis.com/v1/"
        const val SIGNUP = "accounts:signUp"
        const val SIGNIN = "accounts:signInWithPassword"
        const val PASSWORD_RESET = "accounts:sendOobCode"
        const val KEY = "?key=AIzaSyDQAMjXwmEy3Xg-JB7gCG7_s4CtDO-f5j8"
    }

    private val database = AppDatabase.getDatabase(application)

    private val userDao = database.userDao()

    private val firestore = FirebaseFirestore.getInstance()
    private val queue = Volley.newRequestQueue(application)

    fun login(email: String, password: String) = userDao.login(email, password)

    fun createUser(user: User) {
        val params = JSONObject().also {
            it.put("email", user.email)
            it.put("password", user.password)
            it.put("returnSecureToken", true)
        }

        val request = JsonObjectRequest(Request.Method.POST, BASE_URL + SIGNUP + KEY, params,
            { response ->
                user.id = response.getString("localId")
                user.password = response.getString("idToken")

                firestore.collection("user_usr").document(user.id).set(user)
            },
            { error ->
                Log.e(TAG, error.message ?: "Error")
            })
    }

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
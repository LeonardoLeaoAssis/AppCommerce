package br.com.arquitetoandroid.appcommerce.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import br.com.arquitetoandroid.appcommerce.database.AppDatabase
import br.com.arquitetoandroid.appcommerce.model.User
import br.com.arquitetoandroid.appcommerce.model.UserAddress
import com.android.volley.Request
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

    fun login(email: String, password: String): LiveData<User> {
        val liveData = MutableLiveData<User>()

        val params = JSONObject().also {
            it.put("email", email)
            it.put("password", password)
            it.put("returnSecureToken", true)
        }

        val request = JsonObjectRequest(Request.Method.POST, BASE_URL + SIGNIN + KEY, params,
            { response ->
                val localId = response.getString("localId")
                val idToken = response.getString("idToken")

                firestore.collection("user_usr")
                    .document(localId)
                    .get()
                    .addOnSuccessListener {
                        val user = it.toObject(User::class.java)

                        user?.id = localId
                        user?.password = idToken

                        liveData.value = user

                        setUserId(localId)

                        firestore.collection("user_usr")
                            .document(localId)
                            .set(user!!)
                    }
            },
            { error ->
                Log.e(TAG, error.message ?: "Error")
            })

        queue.add(request)

        return liveData
    }

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

                firestore.collection("user_usr")
                    .document(user.id)
                    .set(user)
                    .addOnSuccessListener {
                        Toast.makeText(application, "Usuário ${user.email} cadastrado com sucesso.", Toast.LENGTH_SHORT).show()
                    }
            },
            { error ->
                Log.e(TAG, error.message ?: "Error")
            })

        queue.add(request)
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
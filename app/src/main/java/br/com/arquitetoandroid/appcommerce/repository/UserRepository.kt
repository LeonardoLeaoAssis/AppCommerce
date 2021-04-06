package br.com.arquitetoandroid.appcommerce.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import br.com.arquitetoandroid.appcommerce.model.User
import br.com.arquitetoandroid.appcommerce.model.UserAddress
import br.com.arquitetoandroid.appcommerce.model.UserWithAddress
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

                liveData.value = null
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

    fun resetPassword(email: String) {
        val params = JSONObject().also {
            it.put("email", email)
            it.put("requestType", "PASSWORD_RESET")
        }

        val request = JsonObjectRequest(Request.Method.POST, BASE_URL + PASSWORD_RESET + KEY, params,
            { response ->
                Log.e(TAG, response.keys().toString())
            },
            { error ->
                Log.e(TAG, error.message ?: "Error")
            })

        queue.add(request)
    }

    fun load(userId: String): LiveData<UserWithAddress> {
        val userWithAddress = UserWithAddress()
        val liveData = MutableLiveData<UserWithAddress>()
        val userRef = firestore.collection("user_usr").document(userId)

        userRef.get().addOnSuccessListener {
            val user = it.toObject(User::class.java)
            user?.id = it.id

            userWithAddress.user = user!!

            userRef.collection("user_address_usa").get().addOnCompleteListener{ snap ->
                snap.result?.forEach { doc ->
                    val address = doc.toObject(UserAddress::class.java)
                    address.id = doc.id

                    userWithAddress.addresses.add(address)
                }

                liveData.value = userWithAddress
            }
        }

        return liveData
    }

    fun update(userWithAddress: UserWithAddress): Boolean {
        var updated = false
        val userRef = firestore.collection("user_usr").document(userWithAddress.user.id)

        userRef.set(userWithAddress.user).addOnSuccessListener {
            updated = true
        }

        val addressRef = userRef.collection("user_address_usa")
        val address = userWithAddress.addresses.first()

        if (address.id.isEmpty()) {
            addressRef.add(address).addOnSuccessListener {
                address.id = it.id
                updated = true
            }
        } else {
            addressRef.document(address.id).set(address).addOnSuccessListener {
                updated = true
            }
        }

        return updated
    }

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
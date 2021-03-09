package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.preference.PreferenceManager
import br.com.arquitetoandroid.appcommerce.model.UserAddress
import br.com.arquitetoandroid.appcommerce.model.UserWithAddress
import br.com.arquitetoandroid.appcommerce.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UserProfileActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var textTitle: TextView
    lateinit var imageProfile: ImageView
    lateinit var photoURI: Uri
    lateinit var userProfileName: TextInputEditText
    lateinit var userProfileSurname: TextInputEditText
    lateinit var userProfileEmail: TextInputEditText
    lateinit var userAddress1: TextInputEditText
    lateinit var userAddressNumber: TextInputEditText
    lateinit var userAddress2: TextInputEditText
    lateinit var userAddressCity: TextInputEditText
    lateinit var userAddressCep: TextInputEditText
    lateinit var userAddressState: Spinner
    lateinit var btn_user_profile: Button
    lateinit var userWithAddress: UserWithAddress
    val REQUEST_TAKE_PHOTO = 1

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        textTitle = findViewById(R.id.toolbar_title)
        textTitle.text = getString(R.string.user_profile_title)

        userProfileName = findViewById(R.id.txt_edit_name)
        userProfileSurname = findViewById(R.id.txt_edit_surname)
        userProfileEmail = findViewById(R.id.txt_edit_email)
        userAddress1 = findViewById(R.id.txt_edit_address)
        userAddress2 = findViewById(R.id.txt_edit_address2)
        userAddressNumber = findViewById(R.id.txt_edit_number)
        userAddressCity = findViewById(R.id.txt_edit_city)
        userAddressCep = findViewById(R.id.txt_edit_cep)
        userAddressState = findViewById(R.id.sp_state)
        btn_user_profile = findViewById(R.id.btn_user_profile)

        imageProfile = findViewById(R.id.iv_profile_image)
        imageProfile.setOnClickListener {
            takePicture()
        }

        val profileImage = PreferenceManager.getDefaultSharedPreferences(this).getString(MediaStore.EXTRA_OUTPUT, null)

        if (profileImage == null) {
            photoURI = Uri.parse("/")
            imageProfile.setImageResource(R.drawable.profile_image)
        } else {
            photoURI = Uri.parse(profileImage)
            imageProfile.setImageURI(photoURI)
        }

        btn_user_profile.setOnClickListener {
            update()
        }

        userViewModel.isLogged().observe(this, {
            if (it == null) {
                startActivity(Intent(this, UserLoginActivity::class.java))
                finish()
            } else {
                userWithAddress = it

                userProfileName.setText(it.user.name)
                userProfileSurname.setText(it.user.surname)
                userProfileEmail.setText(it.user.email)

                if (it.addresses.isNotEmpty()) {
                    it.addresses.first().let { address ->
                        userAddress1.setText(address.addressLine1)
                        userAddress2.setText(address.addressLine2)
                        userAddressNumber.setText(address.number)
                        userAddressCity.setText(address.city)
                        userAddressCep.setText(address.zipCode)

                        resources.getStringArray(R.array.states).asList().indexOf(address.state).let { position ->
                            userAddressState.setSelection(position)
                        }
                    }
                }
            }
        })
    }

    private fun update() {
        if (!validate()) {
            return
        }

        userWithAddress.apply {
            user.name = userProfileName.text.toString()
            user.surname = userProfileSurname.text.toString()
            user.email = userProfileEmail.text.toString()
            user.image = photoURI.toString()

            userViewModel.updateUser(user)

            if (addresses.isEmpty()) {
                val userAddress = UserAddress(
                    addressLine1 =  userAddress1.text.toString(),
                    addressLine2 = userAddress2.text.toString(),
                    number = userAddressNumber.text.toString(),
                    city = userAddressCity.text.toString(),
                    zipCode = userAddressCep.text.toString(),
                    state = resources.getStringArray(R.array.states)[userAddressState.selectedItemPosition],
                    userId = user.id
                )

                userViewModel.createAddress(userAddress)
            } else {
                addresses.first().apply {
                    addressLine1 =  userAddress1.text.toString()
                    addressLine2 = userAddress2.text.toString()
                    number = userAddressNumber.text.toString()
                    city = userAddressCity.text.toString()
                    zipCode = userAddressCep.text.toString()
                    state = resources.getStringArray(R.array.states)[userAddressState.selectedItemPosition]

                    userViewModel.updateAddress(this)
                }
            }
        }

        Toast.makeText(this, getString(R.string.user_profile_msg_sucesso), Toast.LENGTH_SHORT).show()
    }

    private fun validate(): Boolean {
        var isValid = true

        userProfileName.apply {
            if (text.isNullOrEmpty()) {
                error = getString(R.string.user_profile_msg_nome)
                isValid = false
            } else {
                error = null
            }
        }

        userProfileSurname.apply {
            if (text.isNullOrEmpty()) {
                error = getString(R.string.user_profile_msg_sobrenome)
                isValid = false
            } else {
                error = null
            }
        }

        userProfileEmail.apply {
            if (text.isNullOrEmpty()) {
                error = getString(R.string.user_profile_msg_email)
                isValid = false
            } else {
                error = null
            }
        }

        userAddress1.apply {
            if (text.isNullOrEmpty()) {
                error = getString(R.string.user_profile_msg_rua_avenida)
                isValid = false
            } else {
                error = null
            }
        }

        userAddressNumber.apply {
            if (text.isNullOrEmpty()) {
                error = getString(R.string.user_profile_msg_numero)
                isValid = false
            } else {
                error = null
            }
        }

        userAddressCity.apply {
            if (text.isNullOrEmpty()) {
                error = getString(R.string.user_profile_msg_cidade)
                isValid = false
            } else {
                error = null
            }
        }

        userAddressCep.apply {
            if (text.isNullOrEmpty()) {
                error = getString(R.string.user_profile_msg_cep)
                isValid = false
            } else {
                error = null
            }
        }

        return isValid
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "PROFILE_${timeStamp}",
            ".jpg",
            storageDir
        )
    }

    private fun takePicture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(this,
                    "br.com.arquitetoandroid.appcommerce.fileprovider",
                    it)

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        PreferenceManager.getDefaultSharedPreferences(this).apply {
            edit().putString(MediaStore.EXTRA_OUTPUT, photoURI.toString()).apply()
        }

        imageProfile.setImageURI(photoURI)
    }

}
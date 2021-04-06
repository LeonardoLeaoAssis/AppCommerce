package br.com.arquitetoandroid.appcommerce

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.arquitetoandroid.appcommerce.databinding.ActivityUserRegisterBinding
import br.com.arquitetoandroid.appcommerce.model.User
import br.com.arquitetoandroid.appcommerce.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.menu_toolbar_layout.view.*

class UserRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserRegisterBinding

    lateinit var registerName: TextInputEditText
    lateinit var registerEmail: TextInputEditText
    lateinit var registerPassword: TextInputEditText

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarLayout.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.appBarLayout.toolbar_title.text = getString(R.string.user_register_title)

        registerName = binding.txtEditName
        registerEmail = binding.txtEditEmail
        registerPassword = binding.txtEditPassword

        binding.btnUserRegister.setOnClickListener {
            if (validate()) {
                val user = User(
                    name = registerName.text.toString(),
                    email = registerEmail.text.toString(),
                    password = registerPassword.text.toString(),
                    image = "",
                    surname = ""
                )

                userViewModel.createUser(user)
                userViewModel.login(user.email, user.password).observe(this, Observer {
                    if (it == null) {
                        Toast.makeText(this, getString(R.string.login_message), Toast.LENGTH_SHORT).show()
                    } else {
                        finish()
                    }
                })
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun validate(): Boolean {
        var isValid = true

        registerName.apply {
            if (text.isNullOrEmpty()) {
                error = getString(R.string.user_profile_msg_nome)
                isValid = false
            } else {
                error = null
            }
        }

        registerEmail.apply {
            if (text.isNullOrEmpty()) {
                error = getString(R.string.user_profile_msg_email)
                isValid = false
            } else {
                error = null
            }
        }

        registerPassword.apply {
            if (text.isNullOrEmpty()) {
                error = context.getString(R.string.user_profile_msg_senha)
                isValid = false
            } else {
                error = null
            }
        }

        return isValid
    }

}
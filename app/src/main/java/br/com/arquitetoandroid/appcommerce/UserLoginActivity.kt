package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import br.com.arquitetoandroid.appcommerce.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class UserLoginActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var textTitle: TextView
    lateinit var btnRegister: Button
    lateinit var btnUserLogin: Button
    lateinit var txtEditLoginEmail: TextInputEditText
    lateinit var txtEditLoginPassword: TextInputEditText

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        textTitle = findViewById(R.id.toolbar_title)
        textTitle.text = getString(R.string.user_login_title)

        txtEditLoginEmail = findViewById(R.id.txt_edit_login_email)
        txtEditLoginPassword = findViewById(R.id.txt_edit_login_password)

        btnUserLogin = findViewById(R.id.btn_user_login)
        btnUserLogin.setOnClickListener {
            userViewModel.login(txtEditLoginEmail.text.toString(), txtEditLoginPassword.text.toString()).observe(this, Observer { user ->
                if (user == null) {
                    Toast.makeText(applicationContext, getString(R.string.login_message), Toast.LENGTH_SHORT).show()
                } else {
                    finish()
                }
            })
        }

        btnRegister = findViewById(R.id.btn_login_register)
        btnRegister.setOnClickListener {
            val intent: Intent = Intent(this, UserRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
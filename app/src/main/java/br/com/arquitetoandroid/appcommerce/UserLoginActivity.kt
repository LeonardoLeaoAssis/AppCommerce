package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.arquitetoandroid.appcommerce.databinding.ActivityUserLoginBinding
import br.com.arquitetoandroid.appcommerce.viewmodel.UserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.menu_toolbar_layout.view.*

class UserLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserLoginBinding

    lateinit var dialogResetPassword: AlertDialog

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarLayout.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.appBarLayout.toolbar_title.text = getString(R.string.user_login_title)

        buildResetPasswordDialog()

        binding.btnUserLogin.setOnClickListener {
            userViewModel.login(binding.txtEditLoginEmail.text.toString(), binding.txtEditLoginPassword.text.toString()).observe(this, Observer { user ->
                if (user == null) {
                    Toast.makeText(applicationContext, getString(R.string.login_message), Toast.LENGTH_SHORT).show()
                } else {
                    finish()
                }
            })
        }

        binding.btnLoginRegister.setOnClickListener {
            val intent: Intent = Intent(this, UserRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.txtForgoutPassword.setOnClickListener {
            dialogResetPassword.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun buildResetPasswordDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_user_reset_password, null)
        val etEmail = view.findViewById<TextInputEditText>(R.id.txt_edit_login_email)

        dialogResetPassword = MaterialAlertDialogBuilder(this)
                .setPositiveButton("Resetar") { dialog, which ->
                    userViewModel.resetPassword(etEmail.text.toString())
                    etEmail.text?.clear()

                    Toast.makeText(this, "Verifique seu email para resetar a senha", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Cancelar") { dialog, which ->
                    etEmail.text?.clear()
                }
                .setIcon(android.R.drawable.ic_dialog_email)
                .setView(view)
                .setTitle("Preencha seu email para resetar sua senha")
                .create()
    }

}
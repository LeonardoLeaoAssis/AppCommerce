package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.arquitetoandroid.appcommerce.databinding.ActivityOrderBinding
import br.com.arquitetoandroid.appcommerce.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.menu_toolbar_layout.view.*

class OrderActivity: AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarLayout.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.appBarLayout.toolbar_title.text = getString(R.string.order_title)

        userViewModel.isLogged().observe(this, Observer {
            if (it == null) {
                startActivity(Intent(this, UserLoginActivity::class.java))
                finish()
            } else {
                val arguments = Bundle()
                arguments.putSerializable("USER", it.user)

                val fragment = OrderFragment()
                fragment.arguments = arguments

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_order, fragment)
                    .commit()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
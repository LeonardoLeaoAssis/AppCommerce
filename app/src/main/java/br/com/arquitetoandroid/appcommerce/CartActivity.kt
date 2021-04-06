package br.com.arquitetoandroid.appcommerce

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.arquitetoandroid.appcommerce.databinding.ActivityCartBinding
import br.com.arquitetoandroid.appcommerce.viewmodel.CartViewModel
import kotlinx.android.synthetic.main.menu_toolbar_layout.view.*

class CartActivity: AppCompatActivity(), CartFragment.Callback {

    private lateinit var binding: ActivityCartBinding

    private val cartViewModel by viewModels<CartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarLayout.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.appBarLayout.toolbar_title.text = getString(R.string.cart_title)

        cartViewModel.cartPrice.observe(this, Observer {
            binding.tvTotal.text = "R$ ${it}"
        })

        val fragment: CartFragment = CartFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_cart, fragment)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun updateCart() {
        cartViewModel.cartPrice.value = CartViewModel.order.price
    }

}
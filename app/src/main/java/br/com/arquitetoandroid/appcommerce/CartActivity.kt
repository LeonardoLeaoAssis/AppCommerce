package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.arquitetoandroid.appcommerce.databinding.ActivityCartBinding
import br.com.arquitetoandroid.appcommerce.viewmodel.CartViewModel
import br.com.arquitetoandroid.appcommerce.viewmodel.OrderViewModel
import br.com.arquitetoandroid.appcommerce.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.menu_toolbar_layout.view.*

class CartActivity: AppCompatActivity(), CartFragment.Callback {

    private lateinit var binding: ActivityCartBinding

    private val cartViewModel by viewModels<CartViewModel>()
    private val orderViewModel by viewModels<OrderViewModel>()
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarLayout.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.appBarLayout.toolbar_title.text = getString(R.string.cart_title)

        binding.btnFinish.setOnClickListener {
            userViewModel.isLogged().observe(this, Observer {
                if (it == null) {
                    startActivity(Intent(this, UserLoginActivity::class.java))
                } else {
                    val fullOrder = CartViewModel.getFullOrder()
                    fullOrder.order.userId = it.user.id
                    orderViewModel.place(fullOrder)

                    startActivity(Intent(this, OrderActivity::class.java))
                }

                finish()
            })
        }

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
        cartViewModel.cartPrice.value = CartViewModel.getFullOrder().order.price
    }

}
package br.com.arquitetoandroid.appcommerce

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.arquitetoandroid.appcommerce.databinding.ActivityCartBinding
import br.com.arquitetoandroid.appcommerce.viewmodel.CartViewModel
import br.com.arquitetoandroid.appcommerce.viewmodel.OrderViewModel
import br.com.arquitetoandroid.appcommerce.viewmodel.UserViewModel
import com.mercadopago.android.px.core.MercadoPagoCheckout
import com.mercadopago.android.px.model.Payment
import com.mercadopago.android.px.model.exceptions.MercadoPagoError
import kotlinx.android.synthetic.main.menu_toolbar_layout.view.*

class CartActivity: AppCompatActivity(), CartFragment.Callback {

    companion object {
        private val TAG = CartActivity::class.java.simpleName
    }

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
                    finish()
                } else {
                    val fullOrder = CartViewModel.getFullOrder()
                    fullOrder.order.userId = it.user.id

                    orderViewModel.place(it, fullOrder).observe(this, Observer { id ->
                        MercadoPagoCheckout.Builder("TEST-08cc9553-ec11-4eff-bc8e-d5ecf76107f3", id)
                            .build()
                            .startPayment(this, 1)
                    })
                }
            })
        }

        cartViewModel.cartPrice.observe(this, Observer {
            binding.tvTotal.text = "R$ ${it}"
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun updateCart() {
        cartViewModel.cartPrice.value = CartViewModel.getFullOrder().order.price
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
                val payment = data?.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT) as Payment

                if (payment.paymentStatus == Payment.StatusCodes.STATUS_APPROVED) {
                    val fullOrder = CartViewModel.getFullOrder()
                    orderViewModel.save(fullOrder, payment)

                    startActivity(Intent(this, OrderActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, getString(R.string.cart_activity_payment_msg), Toast.LENGTH_LONG).show()
                }
            } else if (resultCode == Activity.RESULT_CANCELED && data != null) {
                val error = data.getSerializableExtra(MercadoPagoCheckout.EXTRA_ERROR) as MercadoPagoError
                Log.e(TAG, error.message)
            }
        }
    }

}
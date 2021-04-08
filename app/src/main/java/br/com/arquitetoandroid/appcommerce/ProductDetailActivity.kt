package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import br.com.arquitetoandroid.appcommerce.databinding.ActivityProductDetailConstBinding
import br.com.arquitetoandroid.appcommerce.model.Product
import br.com.arquitetoandroid.appcommerce.model.ProductVariants
import br.com.arquitetoandroid.appcommerce.viewmodel.CartViewModel
import br.com.arquitetoandroid.appcommerce.viewmodel.ProductViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.menu_toolbar_layout.view.*

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailConstBinding

    lateinit var chip_color: ChipGroup
    lateinit var chip_size: ChipGroup

    lateinit var product: Product
    lateinit var productVariants: ProductVariants

    private val productViewModel by viewModels<ProductViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailConstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarLayout.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        product = intent.getSerializableExtra("PRODUCT") as Product

        productViewModel.getProductWithVariantes(product.id).observe(this, Observer {
            productVariants = it
            product = productVariants.product

            binding.appBarLayout.toolbar_title.text = product.title
            binding.tvProductPrice .text = "R$ ${product.price}"
            binding.tvProductDesc.text = product.description

            chip_color = binding.chipGroupColor
            fillColor()

            chip_size = binding.chipGroupSize
            fillSize()

            binding.btnProductBuy.setOnClickListener {
                addToCart()
            }
        })
    }

    private fun addToCart() {
        if (chip_color.checkedChipId == View.NO_ID || chip_size.checkedChipId == View.NO_ID) {
            Toast.makeText(this, getString(R.string.product_detail_cart_msg), Toast.LENGTH_SHORT).show()

            return
        }

        findViewById<Chip>(chip_color.checkedChipId).let {
            productVariants.colors[it.tag as Int].checked = true
        }

        findViewById<Chip>(chip_size.checkedChipId).let {
            productVariants.sizes[it.tag as Int].checked = true
        }

        CartViewModel.addProduct(productVariants, 1)
        startActivity(Intent(this, CartActivity::class.java))
        finish()
    }

    private fun fillColor() {
        val colors = productVariants.colors

        chip_color.removeAllViews()

        for (color in colors) {
            val chip = Chip(ContextThemeWrapper(chip_color.context, R.style.Widget_MaterialComponents_Chip_Choice))
            chip.tag = colors.indexOf(color)
            chip.text = color.name
            chip.isCheckable = true
            chip.chipBackgroundColor = ColorStateList.valueOf(Color.parseColor(color.code))
            chip.chipStrokeWidth = 1.0F
            chip.chipStrokeColor = ColorStateList.valueOf(Color.GRAY)
            chip.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimary)))
            chip_color.addView(chip)
        }
    }

    private fun fillSize() {
        val sizes = productVariants.sizes

        chip_size.removeAllViews()

        for (size in sizes) {
            val chip = Chip(ContextThemeWrapper(chip_size.context, R.style.Widget_MaterialComponents_Chip_Choice))
            chip.tag = sizes.indexOf(size)
            chip.text = size.size
            chip.isCheckable = true
            chip.chipBackgroundColor = ColorStateList.valueOf(Color.WHITE)
            chip.chipStrokeWidth = 1.0F
            chip.chipStrokeColor = ColorStateList.valueOf(Color.GRAY)
            chip.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimary)))
            chip_size.addView(chip)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
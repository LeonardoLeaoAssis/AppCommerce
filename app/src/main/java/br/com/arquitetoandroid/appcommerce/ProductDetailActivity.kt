package br.com.arquitetoandroid.appcommerce

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import br.com.arquitetoandroid.appcommerce.model.Product
import br.com.arquitetoandroid.appcommerce.model.ProductVariants
import br.com.arquitetoandroid.appcommerce.repository.ProductRepository
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ProductDetailActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var productTitle: TextView
    lateinit var productPrice: TextView
    lateinit var productDesc: TextView
    lateinit var chip_color: ChipGroup
    lateinit var chip_size: ChipGroup

    lateinit var product: Product
    lateinit var productVariants: ProductVariants

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail_const)

        val productRepository = ProductRepository(application)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        product = intent.getSerializableExtra("PRODUCT") as Product

        productVariants = productRepository.loadProductById(product.id)
        product = productVariants.product

        productTitle = findViewById(R.id.toolbar_title)
        productTitle.text = product.title

        productPrice = findViewById(R.id.tv_product_price)
        productPrice.text = "R$ ${product.price}"

        productDesc = findViewById(R.id.tv_product_desc)
        productDesc.text = product.description

        chip_color = findViewById(R.id.chip_group_color)
        fillColor()

        chip_size = findViewById(R.id.chip_group_size)
        fillSize()
    }

    private fun fillColor() {
        val colors = productVariants.colors

        for (color in colors) {
            val chip = Chip(ContextThemeWrapper(chip_color.context, R.style.Widget_MaterialComponents_Chip_Choice))
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

        for (size in sizes) {
            val chip = Chip(ContextThemeWrapper(chip_size.context, R.style.Widget_MaterialComponents_Chip_Choice))
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
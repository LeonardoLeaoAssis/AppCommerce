package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.arquitetoandroid.appcommerce.databinding.ActivityProductCategoryBinding
import br.com.arquitetoandroid.appcommerce.interfaces.ProductCategoryCallback
import br.com.arquitetoandroid.appcommerce.model.ProductCategory
import kotlinx.android.synthetic.main.menu_toolbar_layout.view.*

class ProductCategoryActivity : AppCompatActivity(), ProductCategoryCallback {

    private lateinit var binding: ActivityProductCategoryBinding

    var isTablet: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarLayout.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.appBarLayout.toolbar_title.text = getString(R.string.product_category_title)

        isTablet = binding.fragmentProduct != null
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun itemSelected(category: ProductCategory) {
        if (isTablet) {
            val args = Bundle()
            args.putSerializable("CATEGORY", category)

            val fragment: ProductFragment = ProductFragment()
            fragment.arguments = args
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_product, fragment)
                .commit()
        } else {
            val intent: Intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("CATEGORY", category)
            startActivity(intent)
        }
    }

}
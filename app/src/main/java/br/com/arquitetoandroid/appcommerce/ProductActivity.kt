package br.com.arquitetoandroid.appcommerce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.arquitetoandroid.appcommerce.databinding.ActivityProductBinding
import br.com.arquitetoandroid.appcommerce.model.ProductCategory
import kotlinx.android.synthetic.main.menu_toolbar_layout.view.*

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarLayout.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.appBarLayout.toolbar_title.text = getString(R.string.product_title)

        val category: ProductCategory = intent.getSerializableExtra("CATEGORY") as ProductCategory
        val args = Bundle()
        args.putSerializable("CATEGORY", category)

        val fragment: ProductFragment = ProductFragment()
        fragment.arguments = args
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_product, fragment)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
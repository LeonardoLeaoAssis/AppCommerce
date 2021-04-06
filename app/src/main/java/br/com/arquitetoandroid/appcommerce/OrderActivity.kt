package br.com.arquitetoandroid.appcommerce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.arquitetoandroid.appcommerce.databinding.ActivityOrderBinding
import kotlinx.android.synthetic.main.menu_toolbar_layout.view.*

class OrderActivity: AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarLayout.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.appBarLayout.toolbar_title.text = getString(R.string.order_title)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
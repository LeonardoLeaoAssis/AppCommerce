package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.adapter.ProductAdapter
import br.com.arquitetoandroid.appcommerce.adapter.ProductCategoryAdapter
import br.com.arquitetoandroid.appcommerce.databinding.ActivityMainBinding
import br.com.arquitetoandroid.appcommerce.interfaces.ProductCategoryCallback
import br.com.arquitetoandroid.appcommerce.model.ProductCategory
import br.com.arquitetoandroid.appcommerce.viewmodel.HomeBannerViewModel
import br.com.arquitetoandroid.appcommerce.viewmodel.ProductViewModel
import br.com.arquitetoandroid.appcommerce.viewmodel.UserViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.menu_header_layout.view.*
import kotlinx.android.synthetic.main.menu_navigation_view.view.*
import kotlinx.android.synthetic.main.menu_toolbar_layout.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ProductCategoryCallback {

    private lateinit var binding: ActivityMainBinding

    lateinit var textLogin: TextView
    lateinit var recyclerCategory: RecyclerView
    lateinit var recyclerProduct: RecyclerView
    lateinit var imageProfile: ImageView

    private val productViewModel by viewModels<ProductViewModel>()
    private val userViewModel by viewModels<UserViewModel>()
    private val homeBannerViewModel by viewModels<HomeBannerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarLayout.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.appBarLayout.toolbar_title.text = getString(R.string.app_name)

        homeBannerViewModel.load(binding.ivSliderImg).observe(this, Observer {
            binding.tvSliderTitle.text = it.title
            binding.tvSliderSubtitle.text = it.subtitle
        })

        val toogle: ActionBarDrawerToggle = ActionBarDrawerToggle(this, binding.navDrawerLayout, binding.appBarLayout.toolbar, R.string.toogle_open, R.string.toogle_close)
        binding.navDrawerLayout.addDrawerListener(toogle)

        toogle.syncState()

        binding.navDrawerLayout.nav_view.setNavigationItemSelectedListener(this)

        textLogin = binding.navDrawerLayout.nav_view.getHeaderView(0).header_profile_name
        textLogin.setOnClickListener {
            val intent: Intent = Intent(this, UserLoginActivity::class.java)
            startActivity(intent)
        }

        imageProfile = binding.navDrawerLayout.nav_view.getHeaderView(0).header_profile_image

        recyclerCategory = binding.rvMainProductCategory

        val adapterCategory: ProductCategoryAdapter = ProductCategoryAdapter(this)

        productViewModel.featuredCategories.observe(this, Observer {
            adapterCategory.list = it
            adapterCategory.notifyDataSetChanged()
        })

        recyclerCategory.adapter = adapterCategory
        recyclerCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerProduct = binding.rvMainProduct

        val adapterProduct: ProductAdapter = ProductAdapter(this)

        productViewModel.featuredProducts.observe(this, Observer {
            adapterProduct.list = it
            adapterProduct.notifyDataSetChanged()
        })

        recyclerProduct.adapter = adapterProduct
        recyclerProduct.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                val intent: Intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_account -> {
                val intent: Intent = Intent(this, UserProfileActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_category -> {
                val intent: Intent = Intent(this, ProductCategoryActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_orders -> {
                val intent: Intent = Intent(this, OrderActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_settings -> {
                val intent: Intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_cart -> {
                val intent: Intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_logout -> {
                userViewModel.logout()
                finish()
                startActivity(intent)
            }
        }

        binding.navDrawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    override fun onBackPressed() {
        if (binding.navDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.navDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun itemSelected(category: ProductCategory) {
        val intent: Intent = Intent(this, ProductActivity::class.java)
        intent.putExtra("CATEGORY", category)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        userViewModel.isLogged().observe(this, Observer { user ->
            user?.let {
                textLogin.text = "${it.user.name} ${it.user.surname}"

                userViewModel.loadProfileImage(it.user.id, imageProfile)
            }
        })
    }

}

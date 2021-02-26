package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.adapter.ProductAdapter
import br.com.arquitetoandroid.appcommerce.adapter.ProductCategoryAdapter
import br.com.arquitetoandroid.appcommerce.interfaces.ProductCategoryCallback
import br.com.arquitetoandroid.appcommerce.model.ProductCategory
import br.com.arquitetoandroid.appcommerce.repository.ProductRepository
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ProductCategoryCallback {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var textTitle: TextView
    lateinit var textLogin: TextView
    lateinit var recyclerCategory: RecyclerView
    lateinit var recyclerProduct: RecyclerView
    lateinit var imageProfile: ImageView
    lateinit var productRepository: ProductRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productRepository = ProductRepository(application)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        textTitle = findViewById(R.id.toolbar_title)
        textTitle.text = getString(R.string.app_name)

        drawerLayout = findViewById(R.id.nav_drawer_layout)
        val toogle: ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.toogle_open, R.string.toogle_close)
        drawerLayout.addDrawerListener(toogle)

        toogle.syncState()

        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        textLogin = navigationView.getHeaderView(0).findViewById(R.id.header_profile_name)
        textLogin.setOnClickListener {
            val intent: Intent = Intent(this, UserLoginActivity::class.java)
            startActivity(intent)
        }

        imageProfile = navigationView.getHeaderView(0).findViewById(R.id.header_profile_image)

        recyclerCategory = findViewById(R.id.rv_main_product_category)

        val adapterCategory: ProductCategoryAdapter = ProductCategoryAdapter(productRepository.featuredCategories, this)

        recyclerCategory.adapter = adapterCategory
        recyclerCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerProduct = findViewById(R.id.rv_main_product)

        val adapterProduct: ProductAdapter = ProductAdapter(productRepository.featuredProducts, this)

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
                Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
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

        val profileImage = PreferenceManager.getDefaultSharedPreferences(this).getString(MediaStore.EXTRA_OUTPUT, null)

        if (profileImage == null) {
            imageProfile.setImageResource(R.drawable.profile_image)
        } else {
            imageProfile.setImageURI(Uri.parse(profileImage))
        }
    }

}

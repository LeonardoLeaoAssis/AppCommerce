package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.adapter.ProductAdapter
import br.com.arquitetoandroid.appcommerce.adapter.ProductCategoryAdapter
import br.com.arquitetoandroid.appcommerce.interfaces.ProductCategoryCallback
import br.com.arquitetoandroid.appcommerce.model.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        val arrayCategory: List<ProductCategory> = arrayListOf<ProductCategory>(
            ProductCategory("1", "Camisetas", fillRvProduct()),
            ProductCategory("2", "Calças", fillRvProduct()),
            ProductCategory("3", "Jaquetas", fillRvProduct()),
            ProductCategory("4", "Blusas", fillRvProduct()),
            ProductCategory("5", "Tênis", fillRvProduct())
        )
        val adapterCategory: ProductCategoryAdapter = ProductCategoryAdapter(arrayCategory, this)

        recyclerCategory.adapter = adapterCategory
        recyclerCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerProduct = findViewById(R.id.rv_main_product)

        val adapterProduct: ProductAdapter = ProductAdapter(fillRvProduct(), this)

        recyclerProduct.adapter = adapterProduct
        recyclerProduct.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    fun fillRvProduct(): List<Product> {
        val colors: List<ProductColor> = arrayListOf(ProductColor("1", "Vermelho", "#FF0000"),
            ProductColor("2", "Amarelo", "#FFFF00"),
            ProductColor("3", "Verde", "#008000"),
            ProductColor("4", "Azul", "#0000FF"),
            ProductColor("5", "Cinza", "#444444"))

        val sizes: List<ProductSize> = arrayListOf<ProductSize>(
            ProductSize("1", "P"),
            ProductSize("2", "M"),
            ProductSize("3", "G"))

        val product1: Product = Product(
            "1",
            "Camiseta 1",
            ProductCategory("1", "Camisetas"),
            "Vocẽ selecionou a camiseta 1",
            1.00,
            colors,
            sizes,
            arrayListOf<ProductImage>(ProductImage("1", "xxx")))

        val product2: Product = Product(
            "2",
            "Camiseta 2",
            ProductCategory("1", "Camisetas"),
            "Vocẽ selecionou a camiseta 2",
            2.00,
            colors,
            sizes,
            arrayListOf<ProductImage>(ProductImage("1", "xxx")))

        val product3: Product = Product(
            "3",
            "Camiseta 3",
            ProductCategory("1", "Camisetas"),
            "Vocẽ selecionou a camiseta 3",
            3.00,
            colors,
            sizes,
            arrayListOf<ProductImage>(ProductImage("1", "xxx")))

        val product4: Product = Product(
            "4",
            "Camiseta 4",
            ProductCategory("1", "Camisetas"),
            "Vocẽ selecionou a camiseta 4",
            4.00,
            colors,
            sizes,
            arrayListOf<ProductImage>(ProductImage("1", "xxx")))

        val product5: Product = Product(
            "5",
            "Camiseta 5",
            ProductCategory("1", "Camisetas"),
            "Vocẽ selecionou a camiseta 5",
            5.00,
            colors,
            sizes,
            arrayListOf<ProductImage>(ProductImage("1", "xxx")))

        return arrayListOf(product1, product2, product3, product4, product5)
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

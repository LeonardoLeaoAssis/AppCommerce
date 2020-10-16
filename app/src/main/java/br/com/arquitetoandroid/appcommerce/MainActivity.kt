package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.adapter.ProductCategoryAdapter
import br.com.arquitetoandroid.appcommerce.model.ProductCategory
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var textTitle: TextView
    lateinit var textLogin: TextView
    lateinit var recyclerCategory: RecyclerView

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

        val productItem: LinearLayout = findViewById(R.id.ll_product_item)
        productItem.setOnClickListener() {
            val intent: Intent = Intent(this, ProductDetailActivity::class.java)
            startActivity(intent)
        }

        textLogin = navigationView.getHeaderView(0).findViewById(R.id.header_profile_name)
        textLogin.setOnClickListener {
            val intent: Intent = Intent(this, UserLoginActivity::class.java)
            startActivity(intent)
        }

        recyclerCategory = findViewById(R.id.rv_main_product_category)

        val arrayCategory: List<ProductCategory> = arrayListOf<ProductCategory>(ProductCategory("1", "Camisetas"),
                                                                                ProductCategory("2", "Calças"),
                                                                                ProductCategory("3", "Jaquetas"),
                                                                                ProductCategory("4", "Blusas"),
                                                                                ProductCategory("5", "Tênis"))
        val adapterCategory: ProductCategoryAdapter = ProductCategoryAdapter(arrayCategory, this)

        recyclerCategory.adapter = adapterCategory
        recyclerCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                val intent: Intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_account -> {
                val intent: Intent = Intent(this, UserRegisterActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_category -> {
                val intent: Intent = Intent(this, UserProfileActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_orders -> {
                Toast.makeText(this, "Orders", Toast.LENGTH_LONG).show()
            }

            R.id.nav_cart -> {
                Toast.makeText(this, "Cart", Toast.LENGTH_LONG).show()
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

}

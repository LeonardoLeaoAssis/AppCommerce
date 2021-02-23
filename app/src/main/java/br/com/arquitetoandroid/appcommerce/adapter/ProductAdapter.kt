package br.com.arquitetoandroid.appcommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.ProductDetailActivity
import br.com.arquitetoandroid.appcommerce.R
import br.com.arquitetoandroid.appcommerce.model.Product

class ProductAdapter(val list: List<Product>, val context: Context): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_product_image)
        val title: TextView = itemView.findViewById(R.id.tv_product_title)
        val cardView: CardView = itemView.findViewById(R.id.cv_product_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        val product: Product = list[position]
        holder.title.text = product.title
        holder.image.setImageResource(R.drawable.camiseta_mockup)
        holder.cardView.setOnClickListener {
            val intent: Intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("PRODUCT", product)
            context.startActivity(intent)
        }
    }

}
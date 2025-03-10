package br.com.arquitetoandroid.appcommerce.adapter

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.R
import br.com.arquitetoandroid.appcommerce.model.ProductVariants
import br.com.arquitetoandroid.appcommerce.repository.ProductRepository

class ImageSliderAdapter(val context: Context): RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {

    private val productRepository = ProductRepository(context.applicationContext as Application)
    var productVariants: ProductVariants = ProductVariants()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_slider, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_photo)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = productVariants.images[position]

        productRepository.loadImages(productVariants.product, image.path, holder.image)
    }

    override fun getItemCount(): Int = productVariants.images.size

}
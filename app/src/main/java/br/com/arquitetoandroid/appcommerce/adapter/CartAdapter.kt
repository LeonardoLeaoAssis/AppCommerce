package br.com.arquitetoandroid.appcommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.R
import br.com.arquitetoandroid.appcommerce.model.OrderedProduct

class CartAdapter(val list: List<OrderedProduct>, val context: Context): RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_product_image)
        val title: TextView = itemView.findViewById(R.id.tv_product_title)
        val color: TextView = itemView.findViewById(R.id.tv_color)
        val size: TextView = itemView.findViewById(R.id.tv_size)
        val price: TextView = itemView.findViewById(R.id.tv_price)
        val quantity: TextView = itemView.findViewById(R.id.tv_qtd)
        val qtdUp: ImageView = itemView.findViewById(R.id.iv_qtd_up)
        val qtdDown: ImageView = itemView.findViewById(R.id.iv_qtd_down)
        val cardView: CardView = itemView.findViewById(R.id.cv_product_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val orderProduct: OrderedProduct = list[position]
        holder.title.text = orderProduct.product.title
        holder.image.setImageResource(R.drawable.camiseta_mockup)
        holder.color.text = orderProduct.product.colors[0].name
        holder.size.text = orderProduct.product.sizes[0].size
        holder.quantity.text = orderProduct.quantity.toString()

        holder.qtdUp.setOnClickListener {
            orderProduct.quantity += 1
            holder.quantity.text = orderProduct.quantity.toString()
            updatePrice(holder, orderProduct)
        }

        holder.qtdDown.setOnClickListener {
            if (orderProduct.quantity > 0) {
                orderProduct.quantity -= 1
                holder.quantity.text = orderProduct.quantity.toString()
                updatePrice(holder, orderProduct)
            } else {
                Toast.makeText(context, "Quantidade deve ser igual ou superior a zero", Toast.LENGTH_LONG).show()
            }
        }

        updatePrice(holder, orderProduct)
    }

    private fun updatePrice(holder: ViewHolder, orderedProduct: OrderedProduct) {
        holder.price.text = "R$ ${orderedProduct.product.price * orderedProduct.quantity}"
    }

}
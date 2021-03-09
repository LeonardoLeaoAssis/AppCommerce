package br.com.arquitetoandroid.appcommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.R
import br.com.arquitetoandroid.appcommerce.model.Order
import java.text.SimpleDateFormat
import java.util.*

class OrderAdapter(val context: Context): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    var list: List<Order> = emptyList()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.tv_id)
        val method: TextView = itemView.findViewById(R.id.tv_method)
        val status: TextView = itemView.findViewById(R.id.tv_status)
        val time: TextView = itemView.findViewById(R.id.tv_time)
        val total: TextView = itemView.findViewById(R.id.tv_total)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: OrderAdapter.ViewHolder, position: Int) {
        val order: Order = list[position]
        holder.id.text = order.id
        holder.method.text = order.method.message
        holder.status.text = order.status.message
        holder.time.text = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date(order.time))
        holder.total.text = order.price.toString()
    }

}
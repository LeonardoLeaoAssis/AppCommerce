package br.com.arquitetoandroid.appcommerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.adapter.OrderAdapter
import br.com.arquitetoandroid.appcommerce.model.*
import java.util.*

class OrderFragment: Fragment() {

    lateinit var recyclerOrder: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_order, container, false)

        recyclerOrder = view.findViewById(R.id.rv_order)

        val colors: List<ProductColor> = arrayListOf(
            ProductColor("1", "Vermelho", "#FF0000"),
            ProductColor("2", "Amarelo", "#FFFF00"),
            ProductColor("3", "Verde", "#008000"),
            ProductColor("4", "Azul", "#0000FF"),
            ProductColor("5", "Cinza", "#444444")
        )

        val sizes: List<ProductSize> = arrayListOf<ProductSize>(
            ProductSize("1", "P"),
            ProductSize("2", "M"),
            ProductSize("3", "G")
        )

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

        val arrayOrder = arrayListOf<Order>(
            Order(UUID.randomUUID().toString(), System.currentTimeMillis(), Status.PAID, Method.CREDIT_CARD,
                  User("", "", "", "", "", "", emptyList()),
                  arrayListOf(OrderedProduct("1", product1, 1), OrderedProduct("2", product2, 2))),
            Order(UUID.randomUUID().toString(), System.currentTimeMillis(), Status.PENDENT, Method.BOLETO,
                User("", "", "", "", "", "", emptyList()),
                arrayListOf(OrderedProduct("3", product1, 1), OrderedProduct("4", product2, 3))))

        val adapterOrder: OrderAdapter = OrderAdapter(arrayOrder, requireContext())

        recyclerOrder.adapter = adapterOrder
        recyclerOrder.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        return view
    }

}
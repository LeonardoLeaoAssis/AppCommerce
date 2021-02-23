package br.com.arquitetoandroid.appcommerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.adapter.CartAdapter
import br.com.arquitetoandroid.appcommerce.model.*

class CartFragment: Fragment() {

    lateinit var recyclerCart: RecyclerView
    lateinit var order: Order

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_cart, container, false)

        recyclerCart = view.findViewById(R.id.rv_cart)

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

        val adapterCart: CartAdapter = CartAdapter(arrayListOf(OrderedProduct("1", product1, 1),
                                                               OrderedProduct("2", product2, 2),
                                                               OrderedProduct("3", product1, 3),
                                                               OrderedProduct("4", product2, 4)),
                requireContext())

        recyclerCart.adapter = adapterCart
        recyclerCart.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        return view
    }

}
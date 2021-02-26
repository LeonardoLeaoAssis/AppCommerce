package br.com.arquitetoandroid.appcommerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.adapter.ProductAdapter
import br.com.arquitetoandroid.appcommerce.model.ProductCategory
import br.com.arquitetoandroid.appcommerce.repository.ProductRepository

class ProductFragment: Fragment() {

    lateinit var recyclerProduct: RecyclerView
    lateinit var category: ProductCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            category = (arguments?.getSerializable("CATEGORY") as ProductCategory)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_product, container, false)

        recyclerProduct = view.findViewById(R.id.rv_product)

        val productRepository = ProductRepository(requireActivity().application)
        val adapterProduct: ProductAdapter = ProductAdapter(productRepository.loadProductsByCategory(category.id), requireContext())

        recyclerProduct.adapter = adapterProduct
        recyclerProduct.layoutManager = GridLayoutManager(requireContext(), 3)

        return view
    }

}
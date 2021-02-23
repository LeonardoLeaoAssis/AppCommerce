package br.com.arquitetoandroid.appcommerce

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.adapter.ProductCategoryAdapter
import br.com.arquitetoandroid.appcommerce.model.ProductCategory

class ProductCategoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_product_category, container)

        var recyclerCategory : RecyclerView = view.findViewById(R.id.rv_product_category)

        val arrayCategory: List<ProductCategory> = arrayListOf<ProductCategory>(
            ProductCategory("1", "Camisetas", MainActivity().fillRvProduct()),
            ProductCategory("2", "Calças", MainActivity().fillRvProduct()),
            ProductCategory("3", "Jaquetas", MainActivity().fillRvProduct()),
            ProductCategory("4", "Blusas", MainActivity().fillRvProduct()),
            ProductCategory("5", "Tênis", MainActivity().fillRvProduct())
        )

        val adapterCategory: ProductCategoryAdapter = ProductCategoryAdapter(arrayCategory, requireContext())

        recyclerCategory.adapter = adapterCategory
        recyclerCategory.layoutManager = GridLayoutManager(requireContext(), 2)

        return view
    }

}
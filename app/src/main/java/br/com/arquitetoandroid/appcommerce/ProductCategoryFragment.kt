package br.com.arquitetoandroid.appcommerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.adapter.ProductCategoryAdapter
import br.com.arquitetoandroid.appcommerce.viewmodel.ProductViewModel

class ProductCategoryFragment : Fragment() {

    private val productViewModel by viewModels<ProductViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_product_category, container)

        var recyclerCategory : RecyclerView = view.findViewById(R.id.rv_product_category)

        val adapterCategory: ProductCategoryAdapter = ProductCategoryAdapter(requireContext())

        productViewModel.allCategories.observe(viewLifecycleOwner, Observer {
            adapterCategory.list = it
            adapterCategory.notifyDataSetChanged()
        })

        recyclerCategory.adapter = adapterCategory
        recyclerCategory.layoutManager = GridLayoutManager(requireContext(), 2)

        return view
    }

}
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
import br.com.arquitetoandroid.appcommerce.adapter.ProductAdapter
import br.com.arquitetoandroid.appcommerce.databinding.FragmentProductBinding
import br.com.arquitetoandroid.appcommerce.model.ProductCategory
import br.com.arquitetoandroid.appcommerce.viewmodel.ProductViewModel

class ProductFragment: Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    lateinit var recyclerProduct: RecyclerView
    lateinit var category: ProductCategory

    private val productViewModel by viewModels<ProductViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            category = (arguments?.getSerializable("CATEGORY") as ProductCategory)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProductBinding.inflate(inflater)
        val view: View = binding.root

        recyclerProduct = binding.rvProduct

        val adapterProduct: ProductAdapter = ProductAdapter(requireContext())

        productViewModel.getProductsByCategory(category.id).observe(viewLifecycleOwner, Observer {
            adapterProduct.list = it
            adapterProduct.notifyDataSetChanged()
        })

        recyclerProduct.adapter = adapterProduct
        recyclerProduct.layoutManager = GridLayoutManager(requireContext(), 3)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}
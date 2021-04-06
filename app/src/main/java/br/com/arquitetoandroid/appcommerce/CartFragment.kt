package br.com.arquitetoandroid.appcommerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.adapter.CartAdapter
import br.com.arquitetoandroid.appcommerce.databinding.FragmentCartBinding
import br.com.arquitetoandroid.appcommerce.viewmodel.CartViewModel

class CartFragment: Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    lateinit var recyclerCart: RecyclerView

    private val cartViewModel by viewModels<CartViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view: View = binding.root

        recyclerCart = binding.rvCart

        val adapterCart: CartAdapter = CartAdapter(requireContext())

        cartViewModel.orderedProducts.observe(viewLifecycleOwner, Observer {
            adapterCart.list = it
            adapterCart.notifyDataSetChanged()
        })

        recyclerCart.adapter = adapterCart
        recyclerCart.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        return view
    }

    interface Callback {
        fun updateCart()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}
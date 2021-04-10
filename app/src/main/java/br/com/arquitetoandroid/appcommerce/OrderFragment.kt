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
import br.com.arquitetoandroid.appcommerce.adapter.OrderAdapter
import br.com.arquitetoandroid.appcommerce.databinding.FragmentOrderBinding
import br.com.arquitetoandroid.appcommerce.model.User
import br.com.arquitetoandroid.appcommerce.viewmodel.OrderViewModel

class OrderFragment: Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    lateinit var recyclerOrder: RecyclerView
    lateinit var user: User

    private val orderViewModel by viewModels<OrderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            user = arguments?.getSerializable("USER") as User
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val view: View = binding.root

        recyclerOrder = binding.rvOrder

        val adapterOrder: OrderAdapter = OrderAdapter(requireContext())

        orderViewModel.getAllOrderByUser(user.id).observe(viewLifecycleOwner, Observer { order ->
            adapterOrder.list = order
            adapterOrder.notifyDataSetChanged()
        })

        recyclerOrder.adapter = adapterOrder
        recyclerOrder.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}
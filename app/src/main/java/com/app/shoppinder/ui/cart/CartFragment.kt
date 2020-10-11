package com.app.shoppinder.ui.cart

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shoppinder.R
import com.app.shoppinder.data.adapter.CartAdapter
import com.app.shoppinder.ui.main.MainViewModel
import com.app.shoppinder.utils.viewmodelFactory.MyViewModelFactory
import java.text.DecimalFormat

class CartFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private val cartListAdpater = CartAdapter()

    companion object {
        fun newInstance() = CartFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, MyViewModelFactory.getInstance()).get(
            MainViewModel::class.java
        )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.action_cart).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.cart_fragment, container, false)
        val categoryView = view.findViewById<RecyclerView>(R.id.shopping_cart_recyclerView)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        categoryView.layoutManager = layoutManager
        categoryView.adapter = cartListAdpater
        cartListAdpater.setProduct(viewModel.cartList)

        val totalPrice = view.findViewById<TextView>(R.id.total_price)
        totalPrice.text = String.format(getString(R.string.total),totalPrice())
        viewModel.getCart().observe(viewLifecycleOwner, {
            cartListAdpater.setProduct(it)
        })

        return view
    }

    private fun totalPrice(): String {
        var total = 0.0
        for (item in viewModel.cartList) {
            total += (item.quantity!! * item.price!!.toDouble())
        }
        val df = DecimalFormat()
        df.maximumFractionDigits = 2
        return df.format(total)
    }
}

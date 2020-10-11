package com.app.shoppinder.ui.main

import android.os.Bundle
import android.view.*
import android.view.animation.LinearInterpolator
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.app.shoppinder.R
import com.app.shoppinder.data.adapter.CategoryAdapter
import com.app.shoppinder.data.adapter.ProductsAdapter
import com.app.shoppinder.data.model.responseModel.category.DataItem
import com.app.shoppinder.ui.cart.CartFragment
import com.app.shoppinder.utils.viewmodelFactory.MyViewModelFactory
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.yuyakaido.android.cardstackview.*

class MainFragment : Fragment(), CardStackListener, CategoryAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private val productAdapter = ProductsAdapter()
    private val categoryAdapter = CategoryAdapter(this)
    private lateinit var layoutManager: CardStackLayoutManager
    private lateinit var numberCounter: ElegantNumberButton
    private lateinit var stackView: CardStackView
    private lateinit var mMenuTextView: TextView
    var mProductList: List<com.app.shoppinder.data.model.responseModel.product.DataItem>? = null
    private var mSelectId = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        viewModel = ViewModelProvider(viewModelStore, MyViewModelFactory.getInstance()).get(
            MainViewModel::class.java
        )

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("cart", numberCounter.number)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        setHasOptionsMenu(true)
        layoutManager = CardStackLayoutManager(context, this).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }

        val lottieView = view.findViewById<LottieAnimationView>(R.id.lottie)
        val lottieLoader = view.findViewById<LottieAnimationView>(R.id.lottie_load)
        lottieLoader.visibility = View.VISIBLE

        stackView = view.findViewById(R.id.stack_view)
        stackView.layoutManager = layoutManager
        stackView.adapter = productAdapter
        stackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }

        val categoryView = view.findViewById<RecyclerView>(R.id.category_view)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        categoryView.layoutManager = layoutManager
        categoryView.adapter = categoryAdapter

        numberCounter = view.findViewById(R.id.btn)
        numberCounter.setOnValueChangeListener { v, oldValue, newValue ->
        }


        viewModel.getProducts().observe(viewLifecycleOwner, {
            lottieLoader.visibility = View.GONE
            if (it != null && !it.data.isNullOrEmpty()) {
                numberCounter.visibility = View.VISIBLE
                mProductList = it.data
                productAdapter.setProduct(it.data)
                lottieView.visibility = View.GONE
            } else {
                lottieView.visibility = View.VISIBLE
                numberCounter.visibility = View.GONE
            }
        })

        viewModel.getCategory().observe(viewLifecycleOwner, {
            lottieLoader.visibility = View.GONE
            if (it != null && !it.data.isNullOrEmpty()) {
                categoryAdapter.setProduct(it.data)
            }
        })
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val cartItem = menu.findItem(R.id.action_cart)
        cartItem.isVisible = true
        val view = cartItem.actionView as RelativeLayout
        view.setOnClickListener {
            openCartFragment()
        }
        mMenuTextView = view.findViewById(R.id.actionbar_notifcation_textview)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cart -> {
                //   openCartFragment()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun openCartFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.container, CartFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    private var mSwipeTop = false
    override fun onCardDragging(direction: Direction?, ratio: Float) {
        if (direction == null) return
        if (ratio * 100.0 < 30.0) return
        when (direction.ordinal) {

            Direction.Top.ordinal -> {
                if (!mSwipeTop) {
                    mSwipeTop = true
                    if (numberCounter.number.toInt() > 0) {
                        productAdapter.getItem().quantity = numberCounter.number.toInt()
                        viewModel.addToCart(productAdapter.getItem())
                        mMenuTextView.text = viewModel.cartList.size.toString()
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.item_added),
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.no_item),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            Direction.Bottom.ordinal -> {

            }
            Direction.Left.ordinal -> {

            }
            Direction.Right.ordinal -> {

            }

        }
    }

    override fun onCardSwiped(direction: Direction?) {
        numberCounter.number = "0"
    }

    override fun onCardRewound() {
        numberCounter.number = "0"
    }

    override fun onCardCanceled() {
        mSwipeTop = false
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }

    override fun onItemClick(dataItem: DataItem, position: Int) {
        mSelectId = dataItem.id!!
        numberCounter.number = "0"
        val filterList =
            mutableListOf<com.app.shoppinder.data.model.responseModel.product.DataItem>()
        if (!mProductList.isNullOrEmpty()) {
            for (item in mProductList!!) {
                if (!item.categories.isNullOrEmpty()) {
                    for (categ in item.categories) {
                        if (categ!!.id == mSelectId) {
                            filterList.add(item)
                        }
                    }
                }
            }
        }
        productAdapter.setProduct(filterList)
    }


}
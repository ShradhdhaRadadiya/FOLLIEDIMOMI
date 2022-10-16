package com.folliedimomi.fragment


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.folliedimomi.R
import com.folliedimomi._app.getRandomString
import com.folliedimomi.activity.MainActivity
import com.folliedimomi.interfaces.IOnBackPressed
import com.folliedimomi.model.OrderCompleteResponse
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_order_confirm.*

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class OrderConfirmFragment(val orderId: String, val cartId: String, val userId: String) :
    Fragment(),
    KodeinAware, IOnBackPressed {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private val session: Session by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBackToHome.setOnClickListener {
            //val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            //fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            this@OrderConfirmFragment.activity!!.supportFragmentManager.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }

        llSuccess.show()
        MainActivity.cartCount = 0
//        onConfirmOrder()
        /* if (session.getAppSession()!!.isEmpty()) */session.setAppSession(
            String().getRandomString(
                14
            )
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_confirm, container, false)
    }

    private fun onConfirmOrder() {
        Coroutines.main {
            requireActivity().progress_bars_layout.show()
            try {
                val createOrderResponse: OrderCompleteResponse =
                    repository.orderConfirm(cartId = cartId, orderId = orderId, userId = userId)
                if (isAdded && isVisible) {
                    requireActivity().progress_bars_layout.hide()
                    createOrderResponse.let {
                        if (createOrderResponse.status == 1) {
                            llSuccess.show()
                            MainActivity.cartCount = 0
                        } else requireActivity().coordinatorLayout.snackBar(createOrderResponse.message)
                        return@main
                    }
                }
            } catch (e: java.lang.Exception) {
                requireActivity().onException(e)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_cart).isVisible = false
        menu.findItem(R.id.action_search).isVisible = false
    }

    override fun onBackPressed() {
        this@OrderConfirmFragment.activity!!.supportFragmentManager.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}

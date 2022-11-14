package com.folliedimomi.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.folliedimomi.R
import com.folliedimomi.activity.MainActivity
import com.folliedimomi.adapter.OrderAdapter
import com.folliedimomi.model.OrderResponse
import com.folliedimomi.model.Orders
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_order_list.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.IOException

class OrderListFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private val session: Session by instance()
    private val mActivity: MainActivity by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOrders(/*"4"*/session.getUserId().toString())
    }

    @SuppressLint("SetTextI18n")
    private fun getOrders(userId: String) {
        requireActivity().progress_bars_layout.show()
        Coroutines.main {
            try {
                val orderResponse: OrderResponse = repository.getOrder(userId = userId)
                if (isAdded && isVisible) {
                    orderResponse.let {
                        if (orderResponse.status == 1) {
                            tvEmpty.hide()
                            val liOrder: List<Orders> = orderResponse.result
                            rvOrder.layoutManager = LinearLayoutManager(requireContext())
                            rvOrder.adapter = OrderAdapter(requireActivity(), liOrder)
                        } else {
                            tvEmpty.show()
                            requireActivity().coordinatorLayout.snackBar(orderResponse.message)
                        }
                        requireActivity().progress_bars_layout.hide()
                        return@main
                    }
                }
            } catch (e: ApiException) {
                requireActivity().progress_bars_layout.hide()
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: NoInternetException) {
                requireActivity().progress_bars_layout.hide()
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: JsonSyntaxException) {
                requireActivity().progress_bars_layout.hide()
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: IOException) {
                requireActivity().progress_bars_layout.hide()
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_list, container, false)
    }
    override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }
}

package com.folliedimomi.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.folliedimomi.R
import com.folliedimomi.adapter.CouponAdapter
import com.folliedimomi.model.CouponCode
import com.folliedimomi.model.CouponCodeResponse
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.Coroutines
import com.folliedimomi.utils.hide
import com.folliedimomi.utils.onException
import com.folliedimomi.utils.show
import kotlinx.android.synthetic.main.fragment_coupon.*

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.lang.Exception


class CouponFragment : Fragment(R.layout.fragment_coupon), KodeinAware {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private val session: Session by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPromotion()
    }

    private fun getPromotion() {
        Coroutines.main {
            try {
                val couponCodeResponse: CouponCodeResponse = repository.getCoupon(id_cart = "", id_customer = session.getUserId().toString())
                if (isAdded && isVisible) {
                    couponCodeResponse.let {
                        if (couponCodeResponse.status == 1) {
                            val coupon: List<CouponCode> = couponCodeResponse.result
                            if (coupon.isNotEmpty()) {
                                tvEmpty.hide()
                                rvCoupon.layoutManager = LinearLayoutManager(requireContext())
                                rvCoupon.adapter = CouponAdapter(coupon)
                                return@main
                            } else {
                                tvEmpty.show()
                                //requireActivity().coordinatorLayout.snackBar(couponCodeResponse.message)
                            }
                        }else tvEmpty.show()
                    }
                }
            } catch (e: Exception) {
                requireActivity().onException(e)
            }
        }
    }

}

package com.folliedimomi.fragment


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi._app.loadFragment
import com.folliedimomi.sharedPrefrense.Session

import kotlinx.android.synthetic.main.fragment_my_account.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MyAccountFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val session: Session by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tvOrderHistoryAndDetail.setOnClickListener {
            requireActivity().loadFragment(
                OrderListFragment()
            )
        }


        if(session.isServerLoggedIn()){
            tvDeleteAcc.visibility= View.VISIBLE
        }
        tvOrderTracking.setOnClickListener { requireActivity().loadFragment(WebPageFragment("${Constant.URL}it/dove-si-trova-il-mio-pacco")) }
//        tvOrderTracking.setOnClickListener { requireActivity().loadFragment(WebPageFragment("${Constant.URL}it/index.php?controller=mypage")) }
        tvMyAddresses.setOnClickListener { requireActivity().loadFragment(AddressFragment()) }
        tvPersonalInformation.setOnClickListener { requireActivity().loadFragment(ProfileFragment()) }
        tvMyVoucher.setOnClickListener { requireActivity().loadFragment(CouponFragment()) }
        tvDeleteAcc.setOnClickListener { requireActivity().loadFragment(CouponFragment()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_account, container, false)
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

}

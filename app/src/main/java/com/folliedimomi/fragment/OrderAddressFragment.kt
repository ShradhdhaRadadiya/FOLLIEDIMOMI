package com.folliedimomi.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi._app.loadFragment
import com.folliedimomi.adapter.AddressAdapter
import com.folliedimomi.adapter.BillingAdapter
import com.folliedimomi.model.AddressResponse
import com.folliedimomi.model.Addresses
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.gson.JsonSyntaxException


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_order_address.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.IOException

class OrderAddressFragment(mCartId: String, mSecretKey: String, mGrandTotal: String) : Fragment(), AddressAdapter.GetSelectedAddress, KodeinAware, AddAddressFragment.OnAddressAddedListner, BillingAdapter.GetSelectedAddress {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private val session: Session by instance()
    //private val mActivity: MainActivity by instance()

    var cartId: String = ""
    var secretKey: String = ""
    var grandTotal: String = ""
    var idCarrier: String = ""
    var idAddress: String = ""
    var idBillingAddress: String = ""
    var isDifferentBillingAddress: Boolean = false

    init {
        cartId = mCartId
        grandTotal = mGrandTotal
        secretKey = mSecretKey
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //tvGrandTotal.text = grandTotal

        fabAddress.setOnClickListener { requireActivity().loadFragment(AddAddressFragment(this@OrderAddressFragment)) }

        requireActivity().toolbar?.let {
            requireActivity().toolbar.title = resources.getString(R.string.choose_address)
        }

        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (scrollY > oldScrollY) fabAddress.hide()
            else fabAddress.show()
        }

        cbxDifferentBillingAddress.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                isDifferentBillingAddress = true
                if (Constant.myAddressList.isNotEmpty()) idBillingAddress = Constant.myAddressList[0].idAddress.toString()
                rvBillingAddress.show()
                rvBillingAddress.layoutManager = LinearLayoutManager(this@OrderAddressFragment.activity!!)
                rvBillingAddress.adapter = BillingAdapter(requireActivity(), Constant.myAddressList, this@OrderAddressFragment, false, repository)
            } else {
                isDifferentBillingAddress = false
                idBillingAddress = ""
                rvBillingAddress.hide()
            }
        }

        btnNext.setOnClickListener {
            if (idBillingAddress.isEmpty()) {
                idBillingAddress = idAddress
            }
            requireActivity().loadFragment(OrderReviewFragment(cartId,secretKey,grandTotal,idAddress,idCarrier,idBillingAddress))
            //requireActivity().loadFragment(OrderCarrierFragment(cartId,secretKey,grandTotal,idAddress,idBillingAddress))
        }

        tvCancel.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        if (isDifferentBillingAddress) rvBillingAddress.show()
        else rvBillingAddress.hide()

        getAddress(session.getUserId().toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_address, container, false)
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

    @SuppressLint("SetTextI18n")
    private fun getAddress(userId: String) {
        requireActivity().progress_bars_layout.show()
        Coroutines.main {
            try {
                val addressResponse: AddressResponse = repository.getAddresses(customerId = userId)
                if (isAdded && isVisible) {
                    addressResponse.let {
                        if (addressResponse.status == 1) {
                            Constant.myAddressList = addressResponse.result
                            if (Constant.myAddressList.isNotEmpty()) idAddress = Constant.myAddressList[0].idAddress.toString()
                            tvEmptyAddress.hide()
                            rvAddress.show()
                            llGrandTotal.show()
                            fabAddress.show()
                            cbxDifferentBillingAddress.show()
                            rvAddress.layoutManager = LinearLayoutManager(this@OrderAddressFragment.activity!!)
                            rvAddress.adapter = AddressAdapter(requireActivity(), Constant.myAddressList, this@OrderAddressFragment, false, repository)
                        } else {
                            tvEmptyAddress.show()
                            rvAddress.hide()
                            llGrandTotal.hide()
                            fabAddress.show()
                            requireActivity().coordinatorLayout.snackBar(addressResponse.message)
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

    override fun onAddressSelected(addressId: String) {
        idAddress = addressId
    }

    override fun onBillingAddressSelected(addressId: String) {
        idBillingAddress = addressId
    }

    override fun onDeleted() {
        getAddress(session.getUserId().toString())
    }

    override fun onUpdated(address: Addresses) {
        getAddress(session.getUserId().toString())
    }

    override fun onAddressAdded() {
        getAddress(session.getUserId().toString())
    }

}

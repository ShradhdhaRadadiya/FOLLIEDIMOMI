package com.folliedimomi.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.folliedimomi.R
import com.folliedimomi._app.loadFragment
import com.folliedimomi.activity.MainActivity
import com.folliedimomi.adapter.AddressAdapter
import com.folliedimomi.model.AddressResponse
import com.folliedimomi.model.Addresses
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_address.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.IOException

class AddressFragment : Fragment(), AddressAdapter.GetSelectedAddress, KodeinAware,
    AddAddressFragment.OnAddressAddedListner {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private val session: Session by instance()
    private val mActivity: MainActivity by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener { requireActivity().loadFragment(AddAddressFragment(this@AddressFragment)) }

        requireActivity().toolbar?.let {
            requireActivity().toolbar.title = resources.getString(R.string.choose_address)
        }
        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (scrollY > oldScrollY) fab.hide()
            else fab.show()
        }
        getAddress(session.getUserId().toString())
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
                            val addressDetail: List<Addresses> = addressResponse.result
                            tvEmptyAddress.hide()
                            rvAddress.show()
                            rvAddress.layoutManager = LinearLayoutManager(requireActivity())
                            rvAddress.adapter = AddressAdapter(
                                requireActivity(),
                                addressDetail,
                                this@AddressFragment,
                                true,
                                repository
                            )
                        } else {
                            tvEmptyAddress.show()
                            rvAddress.hide()
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        mActivity.updateCount(requireContext(), MainActivity.cartCount)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val item: MenuItem = menu.findItem(R.id.action_cart)
        val search: MenuItem = menu.findItem(R.id.action_search)
        item.isVisible = false
        search.isVisible = false
    }

    override fun onAddressSelected(addressId: String) {

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

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }
}

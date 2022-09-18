package com.folliedimomi.fragment

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi.activity.AdvancedFilterActivity
import com.folliedimomi.adapter.ProductListAdapter
import com.folliedimomi.model.ProductListModel
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import okhttp3.RequestBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class DashboardFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private lateinit var mContext : Context
    //private val mApi: MyApi by instance()
    private var listener: OnFragmentInteractionListener? = null
    //var liPromotion: Array<String> = emptyArray()
    val objects = ArrayList<String>()
    private var sortBy = ""


    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Extract data included in the Intent
            val message = intent.action
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tvFilter.setOnClickListener {
            var intent = Intent(mContext, AdvancedFilterActivity::class.java)
            startActivity(intent)
//            startActivityForResult(intent,100)
//            resultLauncher.launch(Intent(mContext, AdvancedFilterActivity::class.java));
        }

        tvSort.setOnClickListener {
            DialogUtils.showFilterDialog(mContext,
                object : DialogUtils.PostInterface {
                    override fun openGalleryForImage(i: Int) {
                        Log.e("TAG", "tYPE IS ---> $i")
                        when (i) {
                            1 -> sortBy = "sales_desc"
                            2 -> sortBy = "name_asc"
                            3 -> sortBy = "name_desc"
                            4 -> sortBy = "position_asc"
                            5 -> sortBy = "position_desc"
                            6 -> sortBy = "price_asc"
                            7 -> sortBy = "price_desc"

                        }

                    }
                })
        }
    }


    private fun getProductList() {
        Coroutines.main {
           Globals.showProgress(mContext)
            val mMap = HashMap<String, RequestBody>()
            mMap["controller"] = "mobileapi".convertBody()
            mMap["op"] = "category_products".convertBody()
            mMap["id_shop"] = Constant.LANG.convertBody()
            mMap["category_id"] = Constant.CATid.convertBody()
            mMap["page"] = Constant.LANG.convertBody()
            mMap["id_lang"] = Constant.TWO.convertBody()


            try {
                val createOrderResponse: ProductListModel = repository.productList(mMap)
                if (isAdded && isVisible) {
                    Globals.hideProgress()

                    createOrderResponse.let {
                        if (createOrderResponse.status == 1) {

                            rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)
                            rvProduct.adapter = ProductListAdapter(requireActivity(), createOrderResponse.result)

                        } else requireActivity().coordinatorLayout.snackBar(createOrderResponse.message)
                        return@main
                    }
                }
            } catch (e: java.lang.Exception) {mContext.toast(e.message.toString())
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       mContext. registerReceiver(mMessageReceiver, IntentFilter("com.example.andy.CUSTOM_INTENT"));

        getProductList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {

    }
}

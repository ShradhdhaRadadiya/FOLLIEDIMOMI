package com.folliedimomi.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi.model.ProductDetailsModel
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.utils.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import okhttp3.RequestBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.ArrayList


class ProductDetailsFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private lateinit var mContext : Context
    //private val mApi: MyApi by instance()
    private var listener: OnFragmentInteractionListener? = null
    //var liPromotion: Array<String> = emptyArray()
    val objects = ArrayList<String>()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv1.setOnClickListener {
            if (objects.isEmpty()) getProductDetails()
        }
    }

    private fun getProductDetails() {
        Coroutines.main {
            requireActivity().progress_bars_layout.show()
            val mMap = HashMap<String, RequestBody>()
            mMap["controller"] = "mobileapi".convertBody()
            mMap["op"] = "product_detail".convertBody()
            mMap["id_product"] = "275".convertBody()
            mMap["lang_id"] = Constant.LANG.convertBody()


            try {
                val createOrderResponse: ProductDetailsModel = repository.productDetail(mMap)
                if (isAdded && isVisible) {
                    requireActivity().progress_bars_layout.hide()
                    createOrderResponse.let {
                        if (createOrderResponse.status == 1) {


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

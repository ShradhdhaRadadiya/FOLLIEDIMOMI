package com.folliedimomi.fragment

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi._app.loadFragment
import com.folliedimomi.activity.AdvancedFilterActivity
import com.folliedimomi.activity.MainActivity
import com.folliedimomi.activity.MainActivity.Companion.mActivity
import com.folliedimomi.adapter.ProductListAdapter
import com.folliedimomi.databinding.DialogeVideoPlayBinding
import com.folliedimomi.model.ProductListModel
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.folliedimomi.utils.Globals.drawerCatId
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialoge_video_play.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.File
import java.io.IOException


class DashboardFragment(private var drawerCatText: String = "") :
    Fragment(), KodeinAware, ProductListAdapter.GetAddToCartAndVideo {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private lateinit var mContext: Context

    //private val mApi: MyApi by instance()
    private var listener: OnFragmentInteractionListener? = null

    //var liPromotion: Array<String> = emptyArray()
    val objects = ArrayList<String>()
    private var sortBy = "sales_desc"
    private var videoUrl = ""
    private var manufac = ""
    private var start_price = ""
    private var end_price = ""
    private var cate = ""
    private var categoryId = ""
    private val session: Session by instance()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvFilterText.text = drawerCatText

        if(drawerCatText == "Home"){
            iv1.visibility = View.VISIBLE
        }else{
            iv1.visibility = View.GONE
        }

        tvFilter.setOnClickListener {
            val intent = Intent(mContext, AdvancedFilterActivity::class.java)
            startActivity(intent)
        }

        iv1.setOnClickListener {
            if (videoUrl.isNotEmpty()) {
                deleteCache(mContext)
                requireActivity().loadFragment(WebPageFragment(videoUrl ))
//                openDialogeForVideo(videoUrl)
            } else {
                requireContext().toast("Video not found!")
            }
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
                        Log.e(
                            "ProductList",
                            "ProductList -----sorting: $drawerCatId--->$categoryId--->$manufac "
                        )

                        GlobalScope.launch {   getProductList() }

                    }
                })
        }
    }

    private fun openDialogeForVideo(videoUrl: String) {
        val binding: DialogeVideoPlayBinding = DialogeVideoPlayBinding.inflate(
            LayoutInflater.from(context)
        )
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)

        val window: Window = dialog.window!!
        val width = (requireContext().resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        window.setLayout(width, height)
        window.setBackgroundDrawableResource(android.R.color.transparent)
       /* binding.videoView.setVideoPath(videoUrl)
        binding.videoView.setOnPreparedListener {
            it.isLooping = true
            binding.videoView.setMediaController(MediaController(requireContext()));
            binding.videoView.requestFocus();
            binding.videoView.start()
        }
*/

        try {
            binding.webView.getSettings().setJavaScriptEnabled(true)
            binding.webView.setInitialScale(50)
            binding.webView.getSettings().setUseWideViewPort(true)
            binding.webView.setVerticalScrollBarEnabled(false)
            binding.webView.setHorizontalScrollBarEnabled(false)
            binding.webView.loadUrl(videoUrl)
            binding.webView.setWebViewClient(CustomWebViewClient())
        } catch (e: Exception) {
            mContext.toast("handle exception")
        }
        dialog.show()
    }
    fun deleteCache(context: Context) {
        try {
            val dir: File = context.cacheDir
            deleteDir(dir)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory()) {
            val children: Array<String> = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile()) {
            dir.delete()
        } else {
            false
        }
    }

    private fun callDisplyTitleVideoApiCall() {
        //show
        Globals.showProgress(mContext)
        Coroutines.main {
            try {
                var drawerData = repository.getTitleVideo()

                drawerData.result.let {
                    if (drawerData.status == 1) {

                        tvBanner.text = drawerData.result.topBar
                        //imgImage.setImageURI(images[position]);


//                        Glide.with(requireContext()).load(R.drawable.header).into(iv1)
                        videoUrl = drawerData.result.homeVideo
                    }
                    //hide
                    Globals.hideProgress()
                    Log.e(
                        "ProductList",
                        "ProductList -----on video screen: $drawerCatId--->$categoryId--->$manufac "
                    )
                    GlobalScope.launch {   getProductList() }

//                    getProductList()
                    return@main
                }

            } catch (e: ApiException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                Globals.hideProgress()
                requireContext().toast(e.message!!)
            } catch (e: NoInternetException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                Globals.hideProgress()
                requireContext().toast(e.message!!)
            } catch (e: JsonSyntaxException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                Globals.hideProgress()
                requireContext().toast(e.message!!)
            } catch (e: IOException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                Globals.hideProgress()
                requireContext().toast(e.message!!)
            }
        }
    }

   suspend  fun getProductList() {
        if (drawerCatId == 12) {
            categoryId = ""
            manufac = ""
            start_price = ""
            end_price = ""
        }
        Log.e("ProductList", "ProductList -----: $drawerCatId--->$categoryId--->$manufac ")
        Coroutines.main {
            Globals.showProgress(mContext)
            val mMap = HashMap<String, RequestBody>()
            mMap["controller"] = "customerapi".convertBody()
            mMap["op"] = "filter_product".convertBody()
            if (drawerCatId != 0) {
                mMap["id_parent"] = drawerCatId.toString().convertBody()
            }
            mMap["sort_by"] = sortBy.convertBody()

            if (session.getUserId().toString().isNotEmpty()) {
                mMap["id_customer"] = session.getUserId().toString().convertBody()
            }
            mMap["id_lang"] = Constant.LANG.convertBody()
            if (categoryId.trim().isNotEmpty()) {
                mMap["category_id"] = categoryId.convertBody()
            }
            if (manufac.trim().isNotEmpty()) {
                mMap["id_manufacturer"] = manufac.convertBody()
            }
            if (start_price.trim().isNotEmpty()) {
                mMap["start_price"] = start_price.convertBody()
            }
            if (end_price.trim().isNotEmpty()) {
                mMap["end_price"] = end_price.convertBody()
            }

            Log.e("TAG", "data pass is ----> $mMap")
            try {
                val createOrderResponse: ProductListModel = repository.productList(mMap)
                if (isAdded && isVisible) {

                    createOrderResponse.let {
                        if (createOrderResponse.status == 1) {
                            rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)
                            rvProduct.adapter = ProductListAdapter(
                                requireActivity(),
                                createOrderResponse.result.products, this
                            )
                        } else requireActivity().coordinatorLayout.snackBar(createOrderResponse.message)
                    }

                    Globals.hideProgress()
                    return@main

                }
            } catch (e: ApiException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                Globals.hideProgress()
                requireContext().toast(e.message!!)
            } catch (e: NoInternetException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                Globals.hideProgress()
                requireContext().toast(e.message!!)
            } catch (e: JsonSyntaxException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                Globals.hideProgress()
                requireContext().toast(e.message!!)
            } catch (e: IOException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                Globals.hideProgress()
                requireContext().toast(e.message!!)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(drawerCatText == "Home"){
            callDisplyTitleVideoApiCall()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val broadCastReceiver = object : BroadcastReceiver() {
            override fun onReceive(contxt: Context?, intent: Intent?) {
                Log.e("Receive", "receiver cal------------------")
                categoryId = intent?.extras!!.getString("disData").toString()
                manufac = intent.extras!!.getString("featureData").toString()
                end_price = intent.extras!!.getString("end_price").toString()
                start_price = intent.extras!!.getString("start_price").toString()
//                getProductList()
                GlobalScope.launch {   getProductList() }

            }
        }
        LocalBroadcastManager.getInstance(mContext)
            .registerReceiver(broadCastReceiver, IntentFilter("DataAction"))

        return view
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

    override fun onAddToCartProduct(addressId: Int) {
        //show
        Globals.showProgress(mContext)
        Coroutines.main {
            try {

                val mMap = HashMap<String, RequestBody>()
                mMap["controller"] = "mobileapi".convertBody()
                mMap["op"] = "addtocart".convertBody()
                mMap["id_product"] = addressId.toString().convertBody()
                mMap["quantity"] = "1".convertBody()
                mMap["lang_id"] = Constant.LANG.convertBody()
                mMap["shop_id"] = Constant.LANG.convertBody()
                if (session.getUserId().toString().isNotEmpty()) {
                    mMap["id_customer"] = session.getUserId().toString().convertBody()
                } else {
                    mMap["id_customer"] = "0".convertBody()
                }
                mMap["customersessionid"] = session.getAppSession().toString().convertBody()

                Log.e("TAG", "mMAP IS ----> $mMap")

                val drawerData = repository.addToCart(mMap)

                drawerData.result.let {
                    if (drawerData.status == 1) {
                        requireActivity().toast("Prodotto aggiunto con successo al carrello.")
                        drawerData.result.products.let {
                            val product = drawerData.result.products
                            product?.let { MainActivity.cartCount = product.size }
                            mActivity?.updateCount(requireContext(), MainActivity.cartCount)
                        }
                    }
                    //hide
                    Globals.hideProgress()
                    return@main
                }

            } catch (e: ApiException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                Globals.hideProgress()
                requireContext().toast(e.message!!)
            } catch (e: NoInternetException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                Globals.hideProgress()
                requireContext().toast(e.message!!)
            } catch (e: JsonSyntaxException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                Globals.hideProgress()
                requireContext().toast(e.message!!)
            } catch (e: IOException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                Globals.hideProgress()
                requireContext().toast(e.message!!)
            }
        }
    }

    override fun onOpenVideo(videoUrl: String) {
        if (videoUrl.isNotEmpty()) {
            deleteCache(mContext)
            requireActivity().loadFragment(WebPageFragment(videoUrl ))

//            openDialogeForVideo(videoUrl)
        } else {
            requireContext().toast("ERROR LOADING ALL VIDEO")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }
}

/*op:advance_filter
id_parent:12
id_category:17,16
//id_manufacturer:2
start_price:10
end_price:120
*/
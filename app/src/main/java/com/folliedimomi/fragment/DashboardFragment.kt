package com.folliedimomi.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.MediaController
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi.activity.AdvancedFilterActivity
import com.folliedimomi.adapter.ProductListAdapter
import com.folliedimomi.databinding.DialogeVideoPlayBinding
import com.folliedimomi.model.ProductListModel
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.IOException


class DashboardFragment(private var drawerCatText: String = "", private var drawerCatId: Int = 0) :
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
    private var cate = ""
    private var categoryId = ""
    private val session: Session by instance()

    companion object {
        var id_parent = ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvFilterText.text = drawerCatText

        tvFilter.setOnClickListener {
            val intent = Intent(mContext, AdvancedFilterActivity::class.java)
            intent.putExtra("Parent_id",drawerCatId)
            startActivityForResult(intent, 100)
        }

        iv1.setOnClickListener {
            if (videoUrl.isNotEmpty()) {
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

                binding.videoView.setVideoPath(videoUrl)

                binding.videoView.setOnPreparedListener {
                    it.isLooping = true
                    binding.videoView.setMediaController(MediaController(requireContext()));
                    binding.videoView.requestFocus();
                    binding.videoView.start()
                }
                dialog.show()
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
                        getProductList()
                    }
                })
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
                        Glide.with(requireContext()).load(drawerData.result.homeImage).into(iv1)
                        videoUrl = drawerData.result.homeVideo
                    }
                    //hide
                    Globals.hideProgress()

                    getProductList()

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

    private fun getProductList() {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == -1) {
            var data1 = data?.extras!!.getString("disData")


            //todo api call
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        callDisplyTitleVideoApiCall()

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
                }else{
                    mMap["id_customer"] = "0".convertBody()
                }
                mMap["customersessionid"] = session.getAppSession().toString().convertBody()

                Log.e("TAG","mMAP IS ----> $mMap")

                val drawerData = repository.addToCart(mMap)

                drawerData.result.let {
                    if (drawerData.status == 1) {
                        requireActivity().toast(drawerData.message)
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
        //open video dialoge
    }
}

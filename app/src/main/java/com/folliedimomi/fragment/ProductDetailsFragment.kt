package com.folliedimomi.fragment

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi.adapter.BannerAdapter
import com.folliedimomi.model.Gallery
import com.folliedimomi.model.ProductDetailsModel
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_product_detail.*
import okhttp3.RequestBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.IOException


class ProductDetailsFragment(private var p_id: Int) : Fragment(), KodeinAware,
    BannerAdapter.BannerListener {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private lateinit var mContext: Context

    //private val mApi: MyApi by instance()
    private var listener: OnFragmentInteractionListener? = null

    //var liPromotion: Array<String> = emptyArray()
    val objects = ArrayList<String>()

    var myCustomPagerAdapter: BannerAdapter? = null
    val imageList = ArrayList<String>()
    var position = 0
    var proId = 0
    private val session: Session by instance()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProductDetails()

        onAddToCartProduct(proId)

        tvplus.setOnClickListener {
            var data = (tvDigit.text.toString()).toInt()
            if (data > 0) {
                data += 1
                tvDigit.text = data.toString()
            } else {
                requireActivity().toast("Quantity must be grater than 0")
            }
        }
        tvMinus.setOnClickListener {
            var data = (tvDigit.text.toString()).toInt()
            if (data > 0) {
                data -= 1
                tvDigit.text = data.toString()
            } else {
                requireActivity().toast("Quantity must be grater than 0")
            }
        }
    }


     fun onAddToCartProduct(addressId: Int) {
        //show
        Globals.showProgress(mContext)
        Coroutines.main {
            try {

                val mMap = HashMap<String, RequestBody>()
                mMap["controller"] = "mobileapi".convertBody()
                mMap["op"] = "addtocart".convertBody()
                mMap["id_product"] = addressId.toString().convertBody()
                mMap["quantity"] = tvDigit.text.toString().convertBody()
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
    private fun setupGallery(images: ArrayList<String>) {
        var gallery = Gallery(images)
        myCustomPagerAdapter = BannerAdapter(requireContext(), gallery /*images*/, this)
        viewPager.adapter = myCustomPagerAdapter
        viewPager.currentItem = position
        tabLayout.setupWithViewPager(viewPager, true)
        toggleArrowVisibility(position == 0, position == gallery.images.size - 1)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(i: Int) {
                toggleArrowVisibility(i == 0, i == gallery.images.size - 1)
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })
        img_left.setOnClickListener { viewPager.arrowScroll(ViewPager.FOCUS_LEFT) }
        img_right.setOnClickListener { viewPager.arrowScroll(ViewPager.FOCUS_RIGHT) }
    }


    fun toggleArrowVisibility(isAtZeroIndex: Boolean, isAtLastIndex: Boolean) {
        if (isAtZeroIndex) img_left.visibility = View.INVISIBLE else img_left.visibility =
            View.VISIBLE
        if (isAtLastIndex) img_right.visibility = View.INVISIBLE else img_right.visibility =
            View.VISIBLE
    }

    private fun getProductDetails() {
        Coroutines.main {
            requireActivity().progress_bars_layout.show()
            val mMap = HashMap<String, RequestBody>()
            mMap["controller"] = "mobileapi".convertBody()
            mMap["op"] = "product_detail".convertBody()
            mMap["id_product"] = p_id.toString().convertBody()
            mMap["lang_id"] = Constant.LANG.convertBody()
            if (session.getUserId().toString().isNotEmpty()) {
                mMap["id_customer"] = session.getUserId().toString().convertBody()
            } else {
                mMap["id_customer"] = "0".convertBody()
            }

            try {
                val createOrderResponse: ProductDetailsModel = repository.productDetail(mMap)
                if (isAdded && isVisible) {
                    requireActivity().progress_bars_layout.hide()
                    createOrderResponse.let {
                        if (createOrderResponse.status == 1) {

                            var dataDis = createOrderResponse.result
                            proId =  dataDis.idProduct
                            val imageData = arrayListOf<String>()
                            for (item in dataDis.productimages) {
                                imageData.add(item.imageUrl)
                            }
                            setupGallery(imageData)

                            tvName.text = dataDis.categoryName + " / " + dataDis.name
                            tvText1.text = dataDis.finalDisplayPrice
                            tvText21.text = dataDis.name
                            tvDes.text = dataDis.salesNumber
                            tvLongDec.text = Html.fromHtml(dataDis.description).toString()
                            customProgress.progress = dataDis.bar_width

                            when (dataDis.bar_class) {
                                "psb-low" -> customProgress.progressTintList =
                                    ColorStateList.valueOf(
                                        Color.RED
                                    )
                                "psb-medium" -> customProgress.progressTintList =
                                    ColorStateList.valueOf(
                                        Color.BLUE
                                    )
                                "psb-high" -> customProgress.progressTintList =
                                    ColorStateList.valueOf(
                                        Color.CYAN
                                    )
                            }

                            if(dataDis.video.isEmpty()){
                                tvVideoDes.visibility = View.GONE
                            }
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {

    }

    override fun leftClick(pos: Int) {
        viewPager.arrowScroll(ViewPager.FOCUS_LEFT)
    }

    override fun rightClick(pos: Int) {
        viewPager.arrowScroll(ViewPager.FOCUS_RIGHT)
    }
}

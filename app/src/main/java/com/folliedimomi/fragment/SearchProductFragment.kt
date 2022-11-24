package com.folliedimomi.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi._app.loadFragment
import com.folliedimomi.activity.MainActivity
import com.folliedimomi.adapter.ProductListAdapter
import com.folliedimomi.databinding.DialogeVideoPlayBinding
import com.folliedimomi.model.SearchProductModel
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_product_search.*
import okhttp3.RequestBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.File
import java.io.IOException


class SearchProductFragment(private val searchText: String) : Fragment(), KodeinAware,
    ProductListAdapter.GetAddToCartAndVideo {
    override val kodein: Kodein by kodein()

    private val repository: NetworkRepository by instance()
    private val mActivity: MainActivity by instance()
    private var selectedSortBy: String = ""
    private var isNotFirst: Boolean = false
    private val session: Session by instance()
    private lateinit var mContext: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etSearch.setText(searchText)
        etSearch.requestFocus()
        showKeyboard(requireActivity())

        /* etSearch.myOnTextChangedListener {
             if (it.length > 3) getProductFromServer(
                 etSearch.text.toString(),
                 Session(requireContext()).getUserId().toString()
             )
         }
 */
        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //performSearch(etSearch.text.toString())
                hideKeyboard()
                if (etSearch.text.toString().length > 3)
                    getProductFromServer(
                        etSearch.text.toString(),
                        Session(requireContext()).getUserId().toString()
                    )
                /*
                  getProductFromServer(
                      etSearch.text.toString(),
                      Session(requireContext()).getUserId().toString()
                  )*/
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }


    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context


    }

    @SuppressLint("SetTextI18n")
    private fun getProductFromServer(searchText: String, userId: String) {

        Coroutines.main {
            Globals.showProgress(mContext)
            val mMap = HashMap<String, RequestBody>()
            mMap["controller"] = "customerapi".convertBody()
            mMap["op"] = "search_product".convertBody()
            mMap["queryString"] = searchText.convertBody()
            if (session.getUserId().toString().isNotEmpty()) {
                mMap["id_customer"] = session.getUserId().toString().convertBody()
            } else {
                mMap["id_customer"] = "0".convertBody()
            }
            mMap["id_lang"] = Constant.LANG.convertBody()


            try {
                val createOrderResponse: SearchProductModel = repository.searchProduct(mMap)


                try {
                    createOrderResponse.let {
                        if (createOrderResponse.status == 1) {
                            var data = createOrderResponse.result
                            rvCatProduct.layoutManager = GridLayoutManager(requireContext(), 2)
                            rvCatProduct.adapter = ProductListAdapter(
                                mContext as Activity,
                                data, this
                            )
                        } else requireActivity().coordinatorLayout.snackBar(createOrderResponse.message)
                    }
                } catch (e: Exception) {

                }

                Globals.hideProgress()
                return@main


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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_search, container, false)
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
        //val sort: MenuItem = menu.findItem(R.id.action_sort)
        // item.isVisible = false
        // search.isVisible = false
        //sort.isVisible = false
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Fragment.openKeyboard() {
        view?.let { activity?.openKeyboard(it) }
    }

    private fun showKeyboard(activity: Activity) {
        val view = activity.currentFocus
        val methodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        assert(view != null)
        methodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun Context.openKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInputFromWindow(
            view.applicationWindowToken,
            InputMethodManager.SHOW_FORCED,
            0
        )
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
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
            requireActivity().loadFragment(WebPageFragment(videoUrl))

//            openDialogeForVideo(videoUrl)
        } else {
            requireContext().toast("Video not found!")
        }
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
        /*  binding.videoView.setVideoPath(videoUrl)
          binding.videoView.setOnPreparedListener {
              it.isLooping = true
              binding.videoView.setMediaController(MediaController(requireContext()));
              binding.videoView.requestFocus();
              binding.videoView.start()
          }*/
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }
}

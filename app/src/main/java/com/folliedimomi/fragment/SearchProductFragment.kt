package com.folliedimomi.fragment

import android.annotation.SuppressLint
import android.app.Activity
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
import com.folliedimomi._app.myOnTextChangedListener
import com.folliedimomi.activity.MainActivity
import com.folliedimomi.adapter.ProductListAdapter
import com.folliedimomi.model.ProductListModel
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

        etSearch.myOnTextChangedListener {
            if (it.length > 3) getProductFromServer(
                etSearch.text.toString(),
                Session(requireContext()).getUserId().toString()
            )
        }

        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //performSearch(etSearch.text.toString())
                hideKeyboard()
                getProductFromServer(
                    etSearch.text.toString(),
                    Session(requireContext()).getUserId().toString()
                )
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

    override fun onDestroy() {
        super.onDestroy()
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

    }

}

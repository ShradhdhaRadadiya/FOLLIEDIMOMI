package com.folliedimomi.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.folliedimomi.R
import com.folliedimomi._app.loadFragment
import com.folliedimomi.activity.MainActivity
import com.folliedimomi.bottom_sheet.AccountSheet
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class HomeFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val mActivity : MainActivity by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFragment(DashboardFragment())

        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(etSearch.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bNavHome -> {
                    searchDisable()
                    requireActivity().loadFragment(DashboardFragment())

//                    loadFragment(DashboardFragment())
                    true
                }
                R.id.bNavSearch -> {
                    //searchEnable()
                    requireActivity().loadFragment(SearchProductFragment(""))
                    true
                }
                R.id.bNavAccount -> {
                    searchDisable()
                    val accountSheet = AccountSheet.getInstance()
                    accountSheet.show(requireActivity().supportFragmentManager, getString(R.string.my_account))
                    true
                }
                else -> false
            }
        }

        //rlNewArrivalViewAll.visibility = View.VISIBLE
        //rvNewArrival.layoutManager = GridLayoutManager(this@HomeFragment.activity!!, 2)
        //rvNewArrival.adapter = HomeProductAdapter(requireActivity())

        //rlBestSellerViewAll.visibility = View.VISIBLE
        //rvBestSeller.layoutManager = LinearLayoutManager(this@HomeFragment.activity!!, LinearLayoutManager.HORIZONTAL, false)
        //rvBestSeller.layoutManager = GridLayoutManager(this@HomeFragment.activity!!,2)
        //rvBestSeller.adapter = HomeProductAdapter(requireActivity())
    }

    fun performSearch(text: String){
        hideKeyboard()
        //showSnackBar(rootLayout, "Your search $text executed")
//        requireActivity().loadFragment(SearchProductFragment(text))
    }

    fun searchEnable(){
        etSearch.setText("")
        etSearch.visibility = View.VISIBLE
        etSearch.requestFocus()

        val inputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInputFromWindow(rootLayout.windowToken, InputMethodManager.SHOW_FORCED, 0)
    }

    fun searchDisable(){
        hideKeyboard()
        etSearch.visibility = View.GONE
        rootLayout.requestFocus()
    }

    fun showSnackBar(view: View, message:String){
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.anchorView = bottomNavigation
        snackbar.show()
    }

    fun loadFragment(fragment: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.rootLayout, fragment).commit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_cart).isVisible = true
        menu.findItem(R.id.action_search).isVisible = false
    }

}

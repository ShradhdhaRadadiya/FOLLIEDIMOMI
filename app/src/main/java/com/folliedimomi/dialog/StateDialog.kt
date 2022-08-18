package com.pcs.ciprianicouture.dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi.adapter.SearchAdapter

import kotlinx.android.synthetic.main.dialog_choose_product.*

class StateDialog(/*private val productList: List<ProductSearchResult>*/private val productInterface: StateInterface) : DialogFragment(), SearchAdapter.ProductItemClicked {
    private var myInterface: StateInterface? = null

    init {
        this.myInterface = productInterface
    }

    interface StateInterface {
        fun onStateClicked(stateId: String, stateName: String)
    }

    override fun onItemClicked(countryId: String, countryName: String) {
        dismiss()
        myInterface!!.onStateClicked(countryId, countryName)
    }

    private var productAdapter: SearchAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_choose_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvProductName.layoutManager = LinearLayoutManager(this@StateDialog.activity!!)
        productAdapter = SearchAdapter(Constant.stateList, this)
        rvProductName.adapter = productAdapter

        btnCancel.setOnClickListener {
            dismiss()
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                productAdapter!!.filter.filter(etSearch.text.toString())
            }
        })

    }

    companion object {
        //var productDialog : CountryDialog = CountryDialog()
        fun itemSelected(itemId: String) {
            //rvProductName.layoutManager = LinearLayoutManager(this@CountryDialog.activity!!)
            //rvProductName.adapter = ProductSearchAdapter(productList)
            //CountryDialog.dismiss()
            //MainActivity.instance.showSuccess("Selected : $itemId")
        }
    }

}
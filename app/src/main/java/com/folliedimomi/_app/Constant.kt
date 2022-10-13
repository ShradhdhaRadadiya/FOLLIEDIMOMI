package com.folliedimomi._app

import androidx.fragment.app.DialogFragment
import com.folliedimomi.model.Addresses
import com.folliedimomi.model.CountryState
import com.folliedimomi.model.ImageColors


object Constant {
    //companion object {
    var isRunning: Boolean = false
    //}

    const val ROOT_URL: String = "https://folliedimomi.it/"
    const val URL: String = "https://folliedimomi.it/"

    //   const val ROOT_URL : String = "http://cipri-couture.it/ps17/"
//   const val URL : String = "http://cipri-couture.it/ps17/"
//   const val ROOT_URL : String = "https://cipriani-couture.com/"
//   const val URL : String = "https://cipriani-couture.com/"
    const val LANG: String = "1"
    const val CATid: String = "12"
    const val TWO: String = "2"
    const val italy: String = "Italy"
    var productName: String = ""

    //const val ROOT_URL : String = "https://fishbase.ropensci.org/"
    //const val ROOT_URL : String = "http://baleshawebsolutions.com/directportal/app/"

    /** PayPal Sandbox / Live */
    // acc : haresh.dkweb@gmail.com
    // client Id : AYeIgE5lC9ejt2EHMTUszRc1yEh4LTLwg4YvE0HGbakHU2M5nJTxcx8M5vbZ2S_Tzr6IVDOPv8etg-jx
    // secret key : EOy9yndeZVFs3MB1KPvLcZDgDLykq7iU7nSwmQBX-U4WGXpN7aT5EKnPDTvrgtRcNWy24l6rqFjDFrmM

    var countryList: List<CountryState> = listOf()
    var stateList: List<CountryState> = listOf()
    var colorsLIst: List<ImageColors> = listOf()

    var countryListDialog: DialogFragment? = null
    var stateListDialog: DialogFragment? = null

    var myAddressList: List<Addresses> = listOf()
    var myAddress: Addresses = Addresses()
    var myBillingAddress: Addresses = Addresses()
}
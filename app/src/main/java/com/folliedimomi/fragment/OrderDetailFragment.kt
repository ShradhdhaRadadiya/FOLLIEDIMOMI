package com.folliedimomi.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.folliedimomi.R
import com.folliedimomi.activity.MainActivity
import com.folliedimomi.adapter.OrderDetailAdapter
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.gson.JsonSyntaxException
import com.pcs.ciprianicouture.model.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_order_detail.*

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.IOException

class OrderDetailFragment(private val orderId: String) : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private val session: Session by instance()
    private val mActivity: MainActivity by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOrdersDetail(orderId)
    }


    @SuppressLint("SetTextI18n")
    private fun getOrdersDetail(orderId: String) {
        requireActivity().progress_bars_layout.show()
        Coroutines.main {
            try {
                val orderDetailResponse: OrderDetailResponse = repository.getOrderDetail(orderId = orderId)
                if (isAdded && isVisible) {
                    orderDetailResponse.let {
                        if (orderDetailResponse.status == 1) {
                            rlEmptyCard.hide()
                            val orderDetail: OrderDetailResult = orderDetailResponse.result!!
                            val products: List<ProductsOrderDetail> = orderDetail.products!!
                            rvOrderDetail.layoutManager = LinearLayoutManager(requireContext())
                            rvOrderDetail.adapter = OrderDetailAdapter(requireActivity(), products)

                            cvCartMain.show()
                            val date = orderDetail.invoiceDate
                            val mDate = date.split(" ")
                            tvInvoiceDate.text = getString(R.string.date_label) + " " + mDate[0]
                            tvOrderId.text = getString(R.string.order_number_label) + " " + orderDetail.idOrder.toShort()
                            tvReference.text = getString(R.string.reference_label) + " " + orderDetail.reference

                            cvCartTotal.show()
                            //tvTotalDiscount.text = orderDetail.totalDiscounts.toString()
                            //tvSubTotal.text = orderDetail.totalProducts.toString()
                            tvTotalTax.text = orderDetail.totalShipping.toString()
                            tvGrandTotalGroup.text = orderDetail.totalPaid.toString()

                            var totalPay : Double = 0.0
                            for(i in products){
                                if (i.orderCartCustomization.data.isNotEmpty()){
                                    //holder.tvPrice.text  = "${item.cartCustomization[0].custom_price/*.toInt()*/} €"
                                    //var price = item.cartCustomization[0].custom_price.replace(",", ".").toDouble()
                                    //var total = price * item.cartQuantity
                                    //holder.tvGrandTotal.text = /*"€ "+*/"${total.toString().replace(".", ",")} €"
                                    var price = i.orderCartCustomization.customPrice.toDouble()
                                    var newPrice = price + i.price.toDouble()
                                    var total = newPrice * i.orderCartCustomization.quantity
                                    totalPay += total
                                }else{
                                    totalPay += i.price.toDouble()
                                }
                            }

                            var shipping = orderDetail.totalShipping
                            shipping = shipping.replace(",", ".")
                            shipping = shipping.replace("€", "").trim()
                            totalPay += shipping.toDouble()
                            //totalPay -= orderDetail.totalDiscounts

                            tvTotalTax.text = orderDetail.totalShipping.toString()
                            //tvGrandTotalGroup.text = orderDetail.totalPaid.toString()

//                            tvGrandTotalGroup.text = "${totalPay.toString().replace(".", ",")} €"

                            val addresses: AddressInfo = orderDetail.addressInfo
                            val shippingAddress: DelivaryAddress = addresses.delivaryAddress
                            val invoiceAddress: InvoiceAddress = addresses.invoiceAddress

                            cvAddress.show()
                            var myAddress: String = ""
                            myAddress = shippingAddress.firstname + " " + shippingAddress.lastname + "\n" + shippingAddress.phoneMobile +
                                    "\n" + shippingAddress.addressOne + /*"\n" + shippingAddress.addressTwo +*/ "\n" + shippingAddress.city + " " + shippingAddress.postcode

                            var myBillingAddress: String = ""
                            myBillingAddress = invoiceAddress.firstname + " " + invoiceAddress.lastname + "\n" + invoiceAddress.phoneMobile +
                                    "\n" + invoiceAddress.addressOne + /*"\n" + invoiceAddress.addressTwo +*/ "\n" + invoiceAddress.city + " " + invoiceAddress.postcode

                            tvBillingAddress.text = myAddress
                            tvShippingAddress.text = myBillingAddress

                        } else {
                            rlEmptyCard.show()
                            requireActivity().coordinatorLayout.snackBar(orderDetailResponse.message)
                        }
                        requireActivity().progress_bars_layout.hide()
                        return@main
                    }
                }
            } catch (e: ApiException) {
                requireContext().logI(e.message!!)
                requireActivity().progress_bars_layout.hide()
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: NoInternetException) {
                requireContext().logI(e.message!!)
                requireActivity().progress_bars_layout.hide()
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: JsonSyntaxException) {
                requireContext().logI(e.message!!)
                requireActivity().progress_bars_layout.hide()
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: IOException) {
                requireContext().logI(e.message!!)
                requireActivity().progress_bars_layout.hide()
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_detail, container, false)
    }

}

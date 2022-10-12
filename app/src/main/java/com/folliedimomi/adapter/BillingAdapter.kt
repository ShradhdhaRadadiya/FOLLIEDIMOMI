package com.folliedimomi.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.folliedimomi.R
import com.folliedimomi.model.Addresses
import com.folliedimomi.model.ResponseApi
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.utils.ApiException
import com.folliedimomi.utils.Coroutines
import com.folliedimomi.utils.NoInternetException
import com.folliedimomi.utils.snackBar
import com.google.gson.JsonSyntaxException

import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException


class BillingAdapter(
    private val mActivity: Activity, private val myOrderList: List<Addresses>,
    onMyInterface: GetSelectedAddress, private val isAction: Boolean,
    private val repository: NetworkRepository
) : RecyclerView.Adapter<BillingAdapter.ViewHolder>() {
    private var searchOrderList: List<Addresses> = listOf()
    private var myInterface: GetSelectedAddress? = null
    var rowIndex: Int = 0

    init {
        this.searchOrderList = myOrderList
        myInterface = onMyInterface
    }

    interface GetSelectedAddress {
        fun onBillingAddressSelected(addressId: String)
        fun onDeleted()
        fun onUpdated(address: Addresses)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchOrderList!!.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val address = searchOrderList!![position]
        holder.tvNameTo.text = address.firstname + " " + address.lastname
        holder.tvMobile.text = address.address1
        holder.tvAddressOne.text = address.phoneMobile
        holder.tvAddressTwo.text = address.address2
        holder.tvCityToPin.text = address.city + " " + address.postcode

        if (!isAction) {
            holder.tvDelete.visibility = View.INVISIBLE
        }

        holder.tvDelete.setOnClickListener {
            onDeleteAddress(address.idAddress.toString())
        }

        holder.tvEdit.setOnClickListener {
            //myInterface!!.onUpdated(address)
        }

        holder.cvAddress.setOnClickListener {
            rowIndex = position
            // Constant.myAddress = address
            myInterface!!.onBillingAddressSelected(address.idAddress.toString())
            notifyDataSetChanged()
        }

        if (rowIndex == position) {
            holder.cvAddress.setCardBackgroundColor(
                ContextCompat.getColor(
                    mActivity,
                    R.color.colorPrimary
                )
            )
            holder.tvNameTo.setTextColor(ContextCompat.getColor(mActivity, R.color.white))
            holder.tvMobile.setTextColor(ContextCompat.getColor(mActivity, R.color.white))
            holder.tvAddressOne.setTextColor(ContextCompat.getColor(mActivity, R.color.white))
            holder.tvAddressTwo.setTextColor(ContextCompat.getColor(mActivity, R.color.white))
            holder.tvCityToPin.setTextColor(ContextCompat.getColor(mActivity, R.color.white))
            holder.tvEdit.setTextColor(ContextCompat.getColor(mActivity, R.color.white))
            holder.tvDelete.setTextColor(ContextCompat.getColor(mActivity, R.color.white))
            //holder.tvDelete.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.colorPrimary))
        } else {
            holder.cvAddress.setCardBackgroundColor(
                ContextCompat.getColor(
                    mActivity,
                    R.color.white
                )
            )
            holder.tvNameTo.setTextColor(
                ContextCompat.getColor(
                    mActivity,
                    R.color.colorPrimaryText
                )
            )
            holder.tvMobile.setTextColor(
                ContextCompat.getColor(
                    mActivity,
                    R.color.colorSecondaryText
                )
            )
            holder.tvAddressOne.setTextColor(
                ContextCompat.getColor(
                    mActivity,
                    R.color.colorSecondaryText
                )
            )
            holder.tvAddressTwo.setTextColor(
                ContextCompat.getColor(
                    mActivity,
                    R.color.colorSecondaryText
                )
            )
            holder.tvEdit.setTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimaryText))
            holder.tvDelete.setTextColor(
                ContextCompat.getColor(
                    mActivity,
                    R.color.colorPrimaryText
                )
            )
            //holder.tvDelete.backgroundTintList(ContextCompat.getColorStateList(mActivity, R.color.white))
            //holder.tvDelete.setSupportButtonTintList(ContextCompat.getColorStateList(mActivity, R.color.colorPrimary))
            //ViewCompat.setBackgroundTintList(holder.tvDelete, ColorStateList.valueOf(ContextCompat.getColor(mActivity, R.color.white)))

            //var buttonDrawable = holder.tvDelete.background
            //buttonDrawable = DrawableCompat.wrap(buttonDrawable)
            //the color is a direct color int and not a color resource
            //DrawableCompat.setTint(buttonDrawable, Color.RED)
            //holder.tvDelete.background = buttonDrawable

        }

    }

    private fun hide(tv: CardView, hide: Boolean) {
        tv.visibility = if (hide) View.GONE else View.VISIBLE
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNameTo: TextView = itemView.findViewById(R.id.tvNameTo)
        val tvMobile: TextView = itemView.findViewById(R.id.tvMobile)
        val tvAddressOne: TextView = itemView.findViewById(R.id.tvAddressOne)
        val tvAddressTwo: TextView = itemView.findViewById(R.id.tvAddressTwo)
        val tvCityToPin: TextView = itemView.findViewById(R.id.tvCityToPin)

        val tvDelete: TextView = itemView.findViewById(R.id.tvDelete)
        val tvEdit: TextView = itemView.findViewById(R.id.tvEdit)
        val llAction: RelativeLayout = itemView.findViewById(R.id.llAction)
        val cvAddress: CardView = itemView.findViewById(R.id.cvAddress)
    }

    private fun onDeleteAddress(id: String) {
        Coroutines.main {
            try {
                val delete: ResponseApi = repository.deleteAddress(id)
                delete.let {
                    if (delete.status == 1) {
                        mActivity.coordinatorLayout.snackBar(delete.message!!)
                        myInterface!!.onDeleted()
                    } else mActivity.coordinatorLayout.snackBar(delete.message!!)
                    return@main
                }
            } catch (e: ApiException) {
                mActivity.coordinatorLayout.snackBar(e.message!!)
            } catch (e: JsonSyntaxException) {
                mActivity.coordinatorLayout.snackBar(e.message!!)
            } catch (e: NoInternetException) {
                mActivity.coordinatorLayout.snackBar(e.message!!)
            } catch (e: IOException) {
                mActivity.coordinatorLayout.snackBar(e.message!!)
            }
        }
    }
}
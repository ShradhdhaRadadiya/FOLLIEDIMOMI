package com.folliedimomi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.folliedimomi.R
import com.folliedimomi.model.CouponCode


class CouponAdapter(private val liCoupon: List<CouponCode> = listOf()) : RecyclerView.Adapter<CouponAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coupan, parent, false)
        return MyViewHolder(view)/*.listen { position, type ->
                activity.loadFragment(OrderDetailFragment(liOrder[position].idOrder.toString()))
        }*/
    }

    override fun getItemCount(): Int {
        return liCoupon.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: CouponCode = liCoupon[position]
        holder.tvCode.text = /*"€ "+*/item.code
        holder.tvName.text = item.name
        holder.tvDisc.text = /*"€ "+*/item.description
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvCode: TextView = itemView.findViewById(R.id.tvCode)
        val tvDisc: TextView = itemView.findViewById(R.id.tvDisc)
    }
}
package com.folliedimomi.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.folliedimomi.R
import com.folliedimomi._app.listen
import com.folliedimomi._app.loadFragment
import com.folliedimomi.fragment.OrderDetailFragment
import com.folliedimomi.model.Orders


class OrderAdapter(private val activity: Activity, private val liOrder: List<Orders> = listOf()) : RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_orders, parent, false)
        return MyViewHolder(view).listen { position, type ->
                activity.loadFragment(OrderDetailFragment(liOrder[position].idOrder.toString()))
        }
    }

    override fun getItemCount(): Int {
        return liOrder.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: Orders = liOrder[position]
        holder.tvOrderNumber.text = item.reference
        holder.tvPrice.text = /*"€ "+*/item.totalPaid

        val date = item.invoiceDate
        val mDate = date.split(" ")
        holder.tvDate.text = mDate[0]
        holder.tvPaymentMethod.text = item.payment
        //holder.tvOrderStatus.text = item.payment
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOrderNumber: TextView = itemView.findViewById(R.id.tvOrderNumber)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvPaymentMethod: TextView = itemView.findViewById(R.id.tvPaymentMethod)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvOrderStatus: TextView = itemView.findViewById(R.id.tvOrderStatus)
        val cvOrder: CardView = itemView.findViewById(R.id.cvOrder)
    }
}
package com.folliedimomi.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.folliedimomi.R
import com.folliedimomi._app.listen
import com.folliedimomi._app.loadFragment
import com.folliedimomi._app.loadImageInGlide
import com.folliedimomi.fragment.ProductDetailsFragment
import com.folliedimomi.model.ProductListModel


class ProductListAdapter(private val activity: Activity,
                         private val product: List<ProductListModel.Result> = listOf(),
                         private val fromSearch: Boolean = false,
                         private var searchCustom: Boolean = false) :
    RecyclerView.Adapter<ProductListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return MyViewHolder(view).listen { position, type ->
      activity.loadFragment(ProductDetailsFragment(product[position].id))

        }
    }

    override fun getItemCount(): Int {
        return product.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: ProductListModel.Result = product[position]
        holder.tvName.text = item.name
        holder.tvPrice.text =item.price

        activity.loadImageInGlide(holder.imgProduct, item.productImage)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvActualPrice)
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProductImage)
    }
}
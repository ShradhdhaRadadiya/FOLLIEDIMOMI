package com.folliedimomi.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.folliedimomi.R
import com.folliedimomi._app.listen
import com.folliedimomi._app.loadFragmentStack
import com.folliedimomi.fragment.ProductDetailsFragment
import com.folliedimomi.model.ProductListModel


class ProductListAdapter(
    private val activity: Activity,
    private val product: List<ProductListModel.Result.Product> = arrayListOf(),
    private var listnern: GetAddToCartAndVideo,
//                         private val product: List<ProductListModel.Result> = listOf(),
    private val fromSearch: Boolean = false,
    private var searchCustom: Boolean = false
) :
    RecyclerView.Adapter<ProductListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return MyViewHolder(view).listen { position, type ->
            activity.loadFragmentStack(ProductDetailsFragment(product[position].idProduct))
        }
    }


    override fun getItemCount(): Int {
        return product.size
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: ProductListModel.Result.Product = product[position]
        holder.tvName.text = item.name
        holder.tvPrice.text = item.price
        var salesPro = item.salesNumber.split("clienti")
        holder.tvText1.text = salesPro[0] + " clienti"
        holder.tvText2.text = salesPro[1]

        if (item.barWidth == 0) {
            holder.customProgress.visibility = View.INVISIBLE
        }

        if (item.productImage.isNotEmpty())
            Glide.with(activity).load(item.productImage).into(holder.imgProduct)

        if (item.new == 1)
            holder.tvNuovo.visibility = View.VISIBLE
        else
            holder.tvNuovo.visibility = View.INVISIBLE


        holder.customProgress.progress = item.barWidth
        when (item.barClass) {
            "psb-low" -> holder.customProgress.progressTintList =
                ColorStateList.valueOf(activity.resources.getColor(R.color.pro_less))

            "psb-medium" -> holder.customProgress.progressTintList =
                ColorStateList.valueOf(activity.resources.getColor(R.color.pro_med))

            "psb-high" -> holder.customProgress.progressTintList =
                ColorStateList.valueOf(activity.resources.getColor(R.color.pro_high))

        }

        if (item.video.isEmpty()) {
            holder.tvVideo.visibility = View.GONE
        }
        holder.tvDiscount.setOnClickListener {
            listnern.onAddToCartProduct(item.idProduct)
        }
        holder.tvVideo.setOnClickListener {
            listnern.onOpenVideo(item.video[0].video_url)

        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvActualPrice)
        val tvDiscount: TextView = itemView.findViewById(R.id.tvDiscount)
        val tvText1: TextView = itemView.findViewById(R.id.tvText1)
        val tvText2: TextView = itemView.findViewById(R.id.tvText2)
        val tvNuovo: TextView = itemView.findViewById(R.id.tvNuovo)
        val tvVideo: TextView = itemView.findViewById(R.id.tvVideo)
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProductImage)
        val customProgress: ProgressBar = itemView.findViewById(R.id.customProgress)
    }

    interface GetAddToCartAndVideo {
        fun onAddToCartProduct(addressId: Int)
        fun onOpenVideo(videoUrl: String)

    }
}
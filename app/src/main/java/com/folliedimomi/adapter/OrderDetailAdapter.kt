package com.folliedimomi.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.folliedimomi.R
import com.pcs.ciprianicouture.model.ProductsOrderDetail

class OrderDetailAdapter(
    private val activity: Activity,
    private val product: List<ProductsOrderDetail>
) : RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_order_detail, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return product.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: ProductsOrderDetail = product[position]
        setImageInGlide(holder.imgProduct, item.productImage)
        holder.tvName.text = item.productName
        holder.tvPrice.text = activity.getString(R.string.label_price) + item.price
        holder.tvQty.text = activity.getString(R.string.label_qty) + item.productQuantity
        holder.tvGrandTotal.text = activity.getString(R.string.label_total_price) + item.totalPrice
        //holder.tvGrandTotal.text = activity.getString(R.string.label_total_price) + item.totalPrice


        var customization = StringBuilder()
        if (item.orderCartCustomization.data.isNotEmpty()) {
            //customization.append("<![CDATA[ ")
            /*for (i in item.orderCartCustomization.data){
                customization.append("${i.name} : ${i.value} \n")
            }*/

            /*for (i in 1 until  item.orderCartCustomization.data.size) {
                if (item.orderCartCustomization.data[i].value.contentEquals("http")) {
                    customization.append("")
                }else{
                    customization.append("${item.orderCartCustomization.data[i].name} : ${item.orderCartCustomization.data[i].value} \n")
                }
            }*/

            for (i in 0 until item.orderCartCustomization.data.size) {
                if (item.orderCartCustomization.data[i].name == "CARICA PRESCRIZIONE") {
                    customization.append("")
                } else if (item.orderCartCustomization.data[i].name == "CARICA LA TUA FOTO") {
                    customization.append("")
                } /*else if (item.orderCartCustomization.data[i].name == "STEP") {
                    customization.append("")
                } else if (item.orderCartCustomization.data[i].name == "STEP : ") {
                    customization.append("")
                }*/ else if (item.orderCartCustomization.data[i].name == "") {
                    customization.append("Filtro Colorato : ${item.orderCartCustomization.data[i].value} \n")
                } else if (item.orderCartCustomization.data[i].name.isNullOrEmpty()) {
                    customization.append("Filtro Colorato : ${item.orderCartCustomization.data[i].value} \n")
                } else {
                    customization.append("${item.orderCartCustomization.data[i].name} : ${item.orderCartCustomization.data[i].value} \n")
                }//<br />
            }


            if (item.orderCartCustomization.customPrice != null)
                holder.tvPrice.text = "${item.orderCartCustomization.customPrice/*.toInt()*/} €"

            //var total = newPrice * item.cartQuantity
            //holder.tvPrice.text = "${item.cartCustomization[0].custom_price/*.toInt()*/} €"
            //holder.tvPrice.text = "$newPrice €"

            var price = item.orderCartCustomization.customPrice.toDouble()
            var qty = item.orderCartCustomization.quantity.toInt()
            val newPrice = price + item.orderCartCustomization.customPrice.toDouble()
            var total = newPrice * qty
            holder.tvPrice.text = activity.getString(R.string.label_price) + "$newPrice €"
            holder.tvGrandTotal.text = activity.getString(R.string.label_total_price) + "${
                total.toString().replace(".", ",")
            } €"

            holder.tvCustomization.text = customization
        }
        //holder.tvCustomization.text = activity.getString(R.string.label_total_price) + item.totalPrice

    }

    private fun setImageInGlide(img: ImageView, url: String) {
        Glide.with(activity)
            .asBitmap()
            .load(url)
            .apply(RequestOptions().placeholder(R.drawable.ic_launcher_background))
            .into(img)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvQty: TextView = itemView.findViewById(R.id.tvQty)
        val tvGrandTotal: TextView = itemView.findViewById(R.id.tvGrandTotal)
        val tvCustomization: TextView = itemView.findViewById(R.id.tvCustomization)
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProductImage)
    }

}
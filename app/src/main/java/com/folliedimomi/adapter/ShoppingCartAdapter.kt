package com.folliedimomi.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi._app.loadImageInGlide
import com.folliedimomi.model.AddToCartResponse
import com.folliedimomi.model.Product
import com.folliedimomi.model.UpdateQty
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.gson.JsonSyntaxException

import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.RequestBody
import java.io.IOException
import java.lang.Exception
import java.text.DecimalFormat

class ShoppingCartAdapter(
    private val activity: Activity,
    private val product: List<Product>,
    private val repository: NetworkRepository,
    private val cartId: String,
    myInterface: OnCartChange,
    private val session: Session
) : RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder>() {

    var mInterface: OnCartChange? = null

    interface OnCartChange {
        fun onItemDeleted(productId: String)
    }

    init {
        this.mInterface = myInterface
    }

    //var qty: Int = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_cart, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return product.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: Product = product[position]
        activity.loadImageInGlide(holder.imgProduct, item.productImage)
        //setImageInGlide(holder.imgProduct, "https://cipriani-couturecom615916.r.worldssl.net/5570-thickbox_default/deja-vu-.jpg")
        holder.tvName.text = item.name
        val df = DecimalFormat("#.00")
        holder.tvPrice.text = /*"€ "+*/df.format(item.priceWt).replace(".", ",") /*String.format("%.2f", item.priceWt).toDouble().toString()*/  + " €"
        holder.tvGrandTotal.text = /*"€ "+*/ df.format(item.totalWt).replace(".", ",") /*String.format("%.2f", item.totalWt).toDouble().toString()*/+ " €"
        holder.tvSize.text = /*"€ "+*/item.attributes
        holder.etQty.setText(item.cartQuantity.toString())
        item.qty = item.cartQuantity
        var customization = StringBuilder()

/*        val builder = StringBuilder()
        builder.append("Hello")
                .append(" ")
                .append("Baeldung")*/

        if (item.cartCustomization.isNotEmpty()) {
            //customization.append("<![CDATA[ ")
            /*for (i in item.cartCustomization){
                customization.append("${i.label} : ${i.value} \n") //<br />
            }*/
            for (i in 0 until  item.cartCustomization.size) {
                if (item.cartCustomization[i].label == "CARICA PRESCRIZIONE") {
                    customization.append("")
                }else if(item.cartCustomization[i].label == "CARICA LA TUA FOTO"){
                    customization.append("")
                }/*else if(item.cartCustomization[i].label == "STEP : "){
                    customization.append("")
                }else if(item.cartCustomization[i].label == "STEP"){
                    customization.append("")
                }*/ else if(item.cartCustomization[i].label == ""){
                    customization.append("Filtro Colorato : ${item.cartCustomization[i].value} \n")
                } else{
                    if(!(item.cartCustomization[i].value).contains("http")) {
                        customization.append("${item.cartCustomization[i].label} : ${item.cartCustomization[i].value} \n")
                    }
                }//<br />
            }
            //var price : Int =  item.cartCustomization[0].custom_price.toInt()
            var price = item.cartCustomization[0].custom_price.replace(",", ".").toDouble()
            val newPrice = price + item.priceWt
            var total = newPrice * item.cartQuantity
            //holder.tvPrice.text = "${item.cartCustomization[0].custom_price/*.toInt()*/} €"
           // holder.tvPrice.text = "$newPrice €"
            //holder.tvGrandTotal.text = /*"€ "+*/"${total.toString().replace(".", ",")} €"

            holder.tvPrice.text = df.format(newPrice).replace(".", ",")/*String.format("%.2f", newPrice.toDouble()).toString()*/  + " €"
            holder.tvGrandTotal.text = " ${df.format(total).replace(".", ",")} €" /*${String.format("%.2f", total.toDouble()).toString(0).replace(".", ",")}€" */


            //customization.append(" ]]>")
            holder.tvCustomization.show()
            holder.tvCustomization.text = customization
        } else {
            holder.tvCustomization.hide()
        }


        /*holder.imgWishList.setOnClickListener {
            holder.imgWishList.setImageResource(R.drawable.ic_wish_list)
        }*/

        holder.cvProduct.setOnClickListener {
            //MainActivity.instance.loadFragment(ProductDetailFragment(item.productId.toString()))
        }

        holder.imgDelete.setOnClickListener {
            removeFromCart(session.getUserId().toString(), item.idProduct.toString(), item.idProductAttribute.toString(),item.idCustomization)
        }

        holder.imgPlus.setOnClickListener {
            item.qty += 1
            holder.etQty.setText(item.qty.toString())
            //onAddToCart(item.idProduct.toString(),item.idProductAttribute.toString(),  item.qty.toString(), session.getAppSession().toString() , session.getShopId().toString())
            onAddToCart(id_customer = session.getUserId().toString(), id_product = item.idProduct.toString(), id_product_attribute = item.idProductAttribute.toString(), quantity = "1"/*item.qty.toString()*/, customersessionid = session.getAppSession().toString(), upDown = "up", cartId = cartId)
        }

        holder.imgMinus.setOnClickListener {
            if (item.qty > 1) {
                item.qty -= 1
                holder.etQty.setText(item.qty.toString())
                //onAddToCart(item.idProduct.toString(),item.idProductAttribute.toString(),  item.qty.toString(), session.getAppSession().toString() , session.getShopId().toString())
                onAddToCart(id_customer = session.getUserId().toString(), id_product = item.idProduct.toString(), id_product_attribute = item.idProductAttribute.toString(), quantity = "1"/*item.qty.toString()*/, customersessionid = session.getAppSession().toString(), upDown = "down", cartId = cartId)
            }
        }

        /*holder.etQty.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                item.qty = if (s!!.isNotEmpty()) {
                    s.toString().toInt()
                } else {
                    1
                }

                //onAddToCart(item.idProduct.toString(),item.idProductAttribute.toString(),  item.qty.toString(), session.getAppSession().toString() , session.getShopId().toString())
                //onAddToCart(id_customer  = session.getUserId().toString(),id_product = item.idProduct.toString(),id_product_attribute = item.idProductAttribute.toString(),quantity = item.qty.toString(),customersessionid = session.getAppSession().toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })*/
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
        val tvSize: TextView = itemView.findViewById(R.id.tvSize)
        val tvCustomization: TextView = itemView.findViewById(R.id.tvCustomization)
        val tvGrandTotal: TextView = itemView.findViewById(R.id.tvGrandTotal)
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProductImage)
        val imgMinus: ImageView = itemView.findViewById(R.id.imgMinus)
        val imgPlus: ImageView = itemView.findViewById(R.id.imgPlus)
        val imgDelete: ImageView = itemView.findViewById(R.id.imgDelete)
        val cvProduct: CardView = itemView.findViewById(R.id.cvProduct)
        val etQty: EditText = itemView.findViewById(R.id.etQty)
    }

    private fun removeFromCart(userId: String, productId: String, productAttributes: String, idCustomization: Int) {
        activity.progress_bars_layout.show()
        Coroutines.main {
            try {
                val addToCartResponse: AddToCartResponse = repository.removeFromCartGet(cartId, userId, productId, productAttributes, session.getAppSession().toString(),idCustomization)
                addToCartResponse?.let {
                    activity.progress_bars_layout.hide()
                    if (addToCartResponse.status == 1) {
                        mInterface!!.onItemDeleted("")
                        if (addToCartResponse.message == "Opps! cart is empty") {
                            activity.coordinatorLayout.snackBar("Opps! Il tuo carrello è vuoto")
                        } else {
                            activity.coordinatorLayout.snackBar(addToCartResponse.message)
                        }
                    } else {
                        if (addToCartResponse.message == "Opps! cart is empty") {
                            activity.coordinatorLayout.snackBar("Opps! Il tuo carrello è vuoto")
                        } else {
                            activity.coordinatorLayout.snackBar(addToCartResponse.message)
                        }
                    }
                    return@main
                }
            } catch (e: ApiException) {
                activity.coordinatorLayout.snackBar(e.message!!)
            } catch (e: JsonSyntaxException) {
                activity.coordinatorLayout.snackBar(e.message!!)
            } catch (e: NoInternetException) {
                activity.coordinatorLayout.snackBar(e.message!!)
            } catch (e: IOException) {
                activity.coordinatorLayout.snackBar(e.message!!)
            }
        }
    }

    //id_customer  = "",id_product = "",id_product_attribute = "",quantity = "",customersessionid = ""
    private fun onAddToCart(id_customer: String, id_product: String, id_product_attribute: String, quantity: String, customersessionid: String, upDown: String, cartId: String) {
        //private fun onAddToCart(productId: String, productIdAttribute: String, qty: String, userId: String, shopId: String) {
        activity.progress_bars_layout.show()
        Coroutines.main {
            try {
                val addToCartResponse: UpdateQty = repository.onUpdateCartQty(id_customer = id_customer, id_product = id_product, id_product_attribute = id_product_attribute, quantity = quantity, customersessionid = customersessionid, upDown = upDown, idCart = cartId)
                //val addToCartResponse: AddToCartResponse = repository.addToCart(createBodyMap(userId = userId, productId = productId, productIdAttribute = productIdAttribute, qty = qty, shopId = shopId)/**/)

                addToCartResponse.let {
                    activity.progress_bars_layout.hide()
                    if (addToCartResponse.status == 1) {
                        mInterface!!.onItemDeleted("")
                        /*addToCartResponse.result?.let {
                            addToCartResponse.result.products?.let {
                                val product  = addToCartResponse.result.products
                                product?.let { MainActivity.cartCount = product.size }
                                mActivity.updateCount(requireContext(),MainActivity.cartCount)
                            }
                        }*/
                        //requireActivity().coordinatorLayout.snackBar("Prodotto aggiunto con successo al carrello"/*addToCartResponse.message*/)
                    } else {
                        activity.coordinatorLayout.snackBar(addToCartResponse.message)
                    }
                    return@main
                }

            } catch (e: Exception) {
                activity.onException(e)
            }
        }
    }


    private fun createBodyMap(productId: String, productIdAttribute: String, qty: String, userId: String, shopId: String): HashMap<String, RequestBody> {

        val mMap = HashMap<String, RequestBody>()
        mMap["controller"] = "mobileapi".convertBody()
        mMap["op"] = "addtocart".convertBody()
        mMap["id_customer"] = userId.convertBody()
        mMap["id_product"] = productId.convertBody()
        mMap["quantity"] = qty.convertBody()
        mMap["id_product_attribute"] = productIdAttribute.convertBody()
        mMap["lang_id"] = Constant.LANG.convertBody()
        mMap["shop_id"] = shopId.convertBody()
        mMap["customersessionid"] = session.getAppSession()!!.convertBody()

        return mMap
    }


}
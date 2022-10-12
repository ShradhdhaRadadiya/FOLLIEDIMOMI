package com.folliedimomi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.folliedimomi.R
import com.folliedimomi._app.listen
import com.folliedimomi.model.CountryState


class SearchAdapter(
    private val product: List<CountryState>,
    private val productInterface: ProductItemClicked
) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>(), Filterable {
    private var searchProduct: List<CountryState>? = null
    private var myInterface: ProductItemClicked? = null

    init {
        this.searchProduct = product
        this.myInterface = productInterface
    }

    interface ProductItemClicked {
        fun onItemClicked(countryId: String, countryName: String)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    searchProduct = product
                } else {
                    val filteredList = ArrayList<CountryState>()
                    for (row in product) {
                        if (row.name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    searchProduct = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = searchProduct
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
                searchProduct = filterResults!!.values as ArrayList<CountryState>
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_product, parent, false)
        return ViewHolder(view).listen { position, _ ->
            val item = searchProduct!![position]
            myInterface!!.onItemClicked(item.id.toString(), item.name)
        }
    }

    override fun getItemCount(): Int {
        return searchProduct!!.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = searchProduct!![position]
        holder.tvName.text = product.name
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvProductName)
    }
}
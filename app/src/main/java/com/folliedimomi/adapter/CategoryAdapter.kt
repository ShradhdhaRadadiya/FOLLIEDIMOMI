package com.folliedimomi.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.folliedimomi.databinding.ItemCategoryCheckboxIlterBinding
import com.folliedimomi.databinding.ItemCategoryRadioIlterBinding
import com.folliedimomi.model.AdvanceFilterModel


class CategoryAdapter(
    private var dataList: List<AdvanceFilterModel.Result>,
    private var context: Context,
    private var listener: OnProductClick
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)


        return when (viewType) {
            1 -> {
                val msgLeft: ItemCategoryRadioIlterBinding =
                    ItemCategoryRadioIlterBinding.inflate(inflater, parent, false)
                NameViewHolder(msgLeft)
            }
            2 -> {
                val msgRight: ItemCategoryCheckboxIlterBinding =
                    ItemCategoryCheckboxIlterBinding.inflate(inflater, parent, false)
                AdViewHolder(msgRight)
            }
            else -> throw IllegalStateException("Unexpected View Holder value: $viewType")
        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val element = dataList[position] // assuming your list is called "elements"

        when (holder.itemViewType) {
            1 -> {
                val msgLeft: NameViewHolder = holder as NameViewHolder
                msgLeft.bind(element)
                // bind NameViewHolder
            }

            2 -> {
                val msgRight: AdViewHolder = holder as AdViewHolder
                msgRight.bind(element)

                // bind AdViewHolder
            }

        }
    }

    inner class NameViewHolder(itemView: ItemCategoryRadioIlterBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private val binding: ItemCategoryRadioIlterBinding = itemView

        fun bind(cell: AdvanceFilterModel.Result) {
            binding.apply {
                rbCat1.text = cell.title

                rvCat.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                val adapter = AdvanceFilterCatListAdapter(cell.data, context, object :
                    AdvanceFilterCatListAdapter.OnProductClick {

                    override fun onClickOnProduct(isCat: Int, adapterPosition: Int) {
                        listener.onClickCatOnProduct(isCat,adapterPosition)
                    }
                })
                rvCat.adapter = adapter
            }
        }

    }

    inner class AdViewHolder(itemView: ItemCategoryCheckboxIlterBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val binding: ItemCategoryCheckboxIlterBinding = itemView

        fun bind(cell: AdvanceFilterModel.Result) {
            binding.apply {
                rbCat.text = cell.title

                rvCat.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                val adapter = AdvanceFilterFeatureListAdapter(cell.data, context)
                rvCat.adapter = adapter
            }
        }
    }


    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }


    override fun getItemViewType(position: Int): Int {

        return if (dataList[position].type == "radio") {
            1
        } else {
            2
        }
        return super.getItemViewType(position)
    }

    interface OnProductClick {
        fun onClickCatOnProduct(isCat: Int, catId: Int)
    }
}


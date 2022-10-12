package com.folliedimomi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.folliedimomi.activity.AdvancedFilterActivity
import com.folliedimomi.databinding.ItemRadioCatListBinding
import com.folliedimomi.model.AdvanceFilterModel


class AdvanceFilterCatListAdapter(
    private var dataList: List<AdvanceFilterModel.Result.Data>,
    private var context: Context,
    private var listener: OnProductClick
) : RecyclerView.Adapter<AdvanceFilterCatListAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val mBinding =
            ItemRadioCatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(mBinding)
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        try {
            holder.setDataToView(dataList[position])
        } catch (e: Exception) {
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    inner class DataViewHolder(var mBinding: ItemRadioCatListBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun setDataToView(model: AdvanceFilterModel.Result.Data) {

            if (model.categoryId == 0 || model.categoryId == null) {
                mBinding.radio1.text = model.name
            } else {
                mBinding.radio1.text = model.categoryName

            }
            if (AdvancedFilterActivity.categoryData.contains(model.categoryId)) {
                mBinding.radio1.isChecked = true
            }
            mBinding.radio1.setOnCheckedChangeListener { p0, p1 ->
                if (p1) {
                    AdvancedFilterActivity.categoryData.clear()
                    if (model.categoryId == 0) {
                        model.idManufacturer.let { listener.onClickOnProduct(1, it.toInt()) }
                    } else {
                        model.categoryId?.let { listener.onClickOnProduct(1, it) }
                    }
                }
            }

        }
    }


    interface OnProductClick {
        fun onClickOnProduct(isCat: Int, adapterPosition: Int)
    }
}


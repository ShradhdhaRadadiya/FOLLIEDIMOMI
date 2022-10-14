package com.folliedimomi.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.folliedimomi.activity.AdvancedFilterActivity.Companion.categoryData
import com.folliedimomi.activity.AdvancedFilterActivity.Companion.featureData
import com.folliedimomi.databinding.ItemCheckboxCatListBinding
import com.folliedimomi.model.AdvanceFilterModel


class AdvanceFilterFeatureListAdapter(
    private var dataList: List<AdvanceFilterModel.Result.Data>,
    private var context: Context,
    private var listener: OnProductClick

) : RecyclerView.Adapter<AdvanceFilterFeatureListAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val mBinding =
            ItemCheckboxCatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class DataViewHolder(var mBinding: ItemCheckboxCatListBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun setDataToView(model: AdvanceFilterModel.Result.Data) {


            Log.e("model","model is ----> $model")

            if (model.idManufacturer == null || model.idManufacturer.toString() == "0") {
                mBinding.check1.text = model.categoryName
                if (categoryData.contains(model.categoryId)) {
                    mBinding.check1.isChecked = true
                }
            } else {
                mBinding.check1.text = model.name
                if (featureData.contains(model.idManufacturer!!.toInt())) {
                    mBinding.check1.isChecked = true
                }
            }


            mBinding.check1.setOnCheckedChangeListener { _, b ->
                if (b) {
                    if (model.idManufacturer == null || model.idManufacturer.toString() == "0") {
                        if (categoryData.isEmpty()) {
                            categoryData.add(model.categoryId)
                        } else {
                            if (categoryData.contains(model.categoryId)) {
                                categoryData.remove(model.categoryId)
                            } else {
                                categoryData.add(model.categoryId)
                            }
                        }
                    } else {
                        if (featureData.isEmpty()) {
                            featureData.add(model.idManufacturer!!.toInt())
                        } else {
                            if (featureData.contains(model.idManufacturer!!.toInt())) {
                                featureData.remove(model.idManufacturer!!.toInt())
                            } else {
                                featureData.add(model.idManufacturer!!.toInt())
                            }
                        }
                    }
                } else {
                    if (model.idManufacturer == null || model.idManufacturer.toString() == "0") {
                        if (categoryData.isNotEmpty()) {
                            if (categoryData.contains(model.categoryId)) {
                                categoryData.remove(model.categoryId)
                            }
                        }
                    } else {
                        if (featureData.isNotEmpty()) {
                            if (featureData.contains(model.idManufacturer!!.toInt())) {
                                featureData.remove(model.idManufacturer!!.toInt())
                            }
                        }
                    }
                }

                Log.e("TAG","DATA IS ----> CATE---> $categoryData")
                Log.e("TAG","DATA IS ----> featureData---> $featureData")
                listener.onClickOnProduct(1, categoryData.joinToString (","), featureData.joinToString (","))
            }

        }
    }

    interface OnProductClick {
        fun onClickOnProduct(isCat: Int, adapterPosition: String, featureData : String)
    }
}



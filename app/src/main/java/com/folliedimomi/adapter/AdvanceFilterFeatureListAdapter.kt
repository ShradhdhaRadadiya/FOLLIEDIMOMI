package com.folliedimomi.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.folliedimomi.activity.AdvancedFilterActivity.Companion.disData
import com.folliedimomi.activity.AdvancedFilterActivity.Companion.featureData
import com.folliedimomi.databinding.ItemCheckboxCatListBinding
import com.folliedimomi.model.AdvanceFilterModel


class AdvanceFilterFeatureListAdapter(
    private var dataList: List<AdvanceFilterModel.Result.Data>,
    private var context: Context
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

            if (model.idManufacturer.toString() == "0") {
                mBinding.check1.text = model.name
                if(disData.contains(model.name)){
                    mBinding.check1.isChecked = true
                }
            } else {
                mBinding.check1.text = model.name
                if(featureData.contains(model.idManufacturer!!.toInt())){
                    mBinding.check1.isChecked = true
                }
            }


            mBinding.check1.setOnCheckedChangeListener { _, b ->
                if (b) {
                    if (model.idManufacturer.toString() == "0") {
                        if (disData.isEmpty()) {
                            disData.add(model.name.toString())
                        } else {
                            if (disData.contains(model.name)) {
                                disData.remove(model.name)
                            } else {
                                disData.add(model.name.toString())

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
                    if (model.idManufacturer.toString() == "0") {
                        if (disData.isNotEmpty()) {
                            if (disData.contains(model.name)) {
                                disData.remove(model.name)
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

            }

            mBinding.clBook.setOnClickListener {

            }
        }
    }
}


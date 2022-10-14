package com.folliedimomi.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.folliedimomi.adapter.CategoryAdapter
import com.folliedimomi.databinding.ActivityAdvancedFilterBinding
import com.folliedimomi.model.AdvanceFilterModel
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.folliedimomi.utils.Globals.drawerCatId
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.mohammedalaa.seekbar.DoubleValueSeekBarView
import com.mohammedalaa.seekbar.OnDoubleValueSeekBarChangeListener
import kotlinx.android.synthetic.main.activity_advanced_filter.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progress_bars_layout
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.IOException


class AdvancedFilterActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by kodein()
    private lateinit var mBinding: ActivityAdvancedFilterBinding
    private val currentContext = this@AdvancedFilterActivity
    private val session: Session by instance()
    private val repository: NetworkRepository by instance()
    private var filterData: ArrayList<AdvanceFilterModel.Result> = arrayListOf()
    private lateinit var adapter: CategoryAdapter
    private var disponibilita = ""
    private var featured = "0"
    private var catId = "0"
    private var parent_id = "0"
    private  var start_price = 0
    private  var end_price = 0

    companion object {
        var disData = arrayListOf<String>()
        var featureData = arrayListOf<Int>()
        var categoryData = arrayListOf<Int>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAdvancedFilterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        mBinding.imgFilter.setOnClickListener { onBackPressed() }
        parent_id = drawerCatId.toString()

        callAdvanceFilterApiCall()



        rangeSeekbar.setOnRangeSeekBarViewChangeListener(object :
            OnDoubleValueSeekBarChangeListener {
            override fun onValueChanged(
                @Nullable seekBar: DoubleValueSeekBarView?,
                min: Int,
                max: Int,
                fromUser: Boolean
            ) {
                start_price = min
                end_price = max
            }

            override fun onStartTrackingTouch(
                @Nullable seekBar: DoubleValueSeekBarView?,
                min: Int,
                max: Int
            ) {
            }

            override fun onStopTrackingTouch(
                @Nullable seekBar: DoubleValueSeekBarView?,
                min: Int,
                max: Int
            ) {
            }
        })

        mBinding.btnApply.setOnClickListener {

          /*  if(rangeSeekbar.visibility == View.VISIBLE){
                start_price = rangeSeekbar.currentMinValue
                end_price = rangeSeekbar.currentMaxValue
            }else {
                rangeSeekbar.visibility = View.GONE
                start_price = 0
                end_price = 0
            }*/

            val intent = Intent()
            intent.action = "DataAction"
            intent.flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
            intent.putExtra("disData", categoryData.joinToString (",") )
            intent.putExtra("featureData",  featureData.joinToString(","))
            intent.putExtra("start_price",  start_price)
            intent.putExtra("end_price", end_price)
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            finish()
        }
    }


    private fun callAdvanceFilterApiCall() {
        //show

        this.progress_bars_layout.show()
        Coroutines.main {
            try {
//              var  products = repository.getAdvanceFilter("12", "17,16", "2", "10", "120")
                Log.e("TAG","ADVANCE FILTER IS parent_id--> $parent_id")
                Log.e("TAG","ADVANCE FILTER IS catId--> $catId")
                Log.e("TAG","ADVANCE FILTER IS featured--> $featured")
                Log.e("TAG","ADVANCE FILTER IS start_price--> $start_price")
                Log.e("TAG","ADVANCE FILTER IS end_price--> $end_price")
            var products = repository.getAdvanceFilter(parent_id, catId, featured, start_price.toString(), end_price.toString())


                if(products.get("status").toString() == "1"){
                    var advancdRes = products.getAsJsonArray("result")
                    Log.e("TAG","ADVANCE FILTER IS --> ${advancdRes}")

                    filterData.clear()

                    for (i in 0 until advancdRes.size()){
                        Log.e("TAG","ADVANCE ITEM IS --> ${advancdRes[i].asJsonObject}")
                        if(advancdRes[i].asJsonObject.get("title").toString().contains("Categories") || advancdRes[i].asJsonObject.get("title").toString().contains("Marca")){
                            val `object`: AdvanceFilterModel.Result = Gson().fromJson(advancdRes[i].asJsonObject, AdvanceFilterModel.Result::class.java)
                            filterData.add(`object`)
                        }else if(advancdRes[i].asJsonObject.get("title").toString().contains("Prezzo") ){
                            start_price = (advancdRes[i].asJsonObject.get("data").asJsonObject.get("start_price").toString()).toInt()
                            end_price = (advancdRes[i].asJsonObject.get("data").asJsonObject.get("end_price").toString()).toInt()
                        }
                    }

                    if(start_price == 0){
                        rangeSeekbar.visibility = View.GONE
                    }else {
                        rangeSeekbar.visibility =  View.VISIBLE
                        rangeSeekbar.minValue = start_price
                        rangeSeekbar.maxValue = end_price
                    }

                    mBinding.rvProduct.show()
                    mBinding.tvEmptyText.hide()
                    setAdapter(filterData)
                }else{
                    mBinding.rvProduct.hide()
                    mBinding.tvEmptyText.show()
                    toast(products.get("message").toString())
                }
                    //hide
                    this.progress_bars_layout.hide()
                    return@main


            } catch (e: ApiException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                this.progress_bars_layout.hide()

                toast(e.message!!)
            } catch (e: NoInternetException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                this.progress_bars_layout.hide()
                toast(e.message!!)
            } catch (e: JsonSyntaxException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                this.progress_bars_layout.hide()

                toast(e.message!!)
            } catch (e: IOException) {
                Log.i("OkHttp", "Response : ${e.message}")
                //hide
                this.progress_bars_layout.hide()

                toast(e.message!!)
            }
        }
    }


    private fun setAdapter(liProducts: List<AdvanceFilterModel.Result>) {
        mBinding.rvProduct.layoutManager =
            LinearLayoutManager(currentContext, LinearLayoutManager.VERTICAL, false)
        adapter = CategoryAdapter(liProducts, currentContext, object :
            CategoryAdapter.OnProductClick {
            override fun onClickCatOnProduct(isCat: Int, catId1: String, featureData: String) {
                catId =  catId1
                featured = featureData

                callAdvanceFilterApiCall()
            }
        })
        mBinding.rvProduct.adapter = adapter
    }
}
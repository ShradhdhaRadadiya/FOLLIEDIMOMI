package com.folliedimomi.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.folliedimomi.adapter.CategoryAdapter
import com.folliedimomi.databinding.ActivityAdvancedFilterBinding
import com.folliedimomi.model.AdvanceFilterModel
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.activity_main.*
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
    private var filterData: List<AdvanceFilterModel.Result> = arrayListOf()
    private lateinit var adapter: CategoryAdapter
    private var disponibilita = ""
    private var featured = "0"
    private var catId = "0"
    private var parent_id = "0"

    companion object {
        var disData = arrayListOf<String>()
        var featureData = arrayListOf<Int>()
        var categoryData = arrayListOf<Int>()
        var products: AdvanceFilterModel? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAdvancedFilterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        mBinding.imgFilter.setOnClickListener { onBackPressed() }
        parent_id = intent.getStringExtra("Parent_id").toString()
        if (products != null) {
            setAdapter(products?.result!!)
        } else {
            callAdvanceFilterApiCall()
        }

        mBinding.btnApply.setOnClickListener {

            val dataDis = arrayListOf<String>()
            val dataFeatire = arrayListOf<Int>()

            for (i in products!!.result) {
                for (i1 in i.data) {
                    if (i1.categoryId.toString() == "0") {
                        dataDis.add(i1.name.toString())
                    } else {
                        dataFeatire.add(i1.idManufacturer!!.toInt())
                    }
                }
            }

            val tempDis = arrayListOf<String>()
            for (i in disData.indices) {
                if (dataDis.contains(disData[i])) {
                    tempDis.add(disData[i])
                }
            }

            val feaDis = arrayListOf<Int>()
            for (i in featureData.indices) {
                if (dataFeatire.contains(featureData[i])) {
                    feaDis.add(featureData[i])
                }
            }

            disData = tempDis
            featureData = feaDis

            Log.e("TAG","disData IS ---> $disData")
            Log.e("TAG","featureData IS ---> $featureData")

            val intent = Intent()
            intent.putExtra("disData", disData.joinToString())
            intent.putExtra("featureData", featureData.joinToString())
            intent.putExtra("catId", catId)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }


    private fun callAdvanceFilterApiCall() {
        //show

        this.progress_bars_layout.show()
        Coroutines.main {
            try {
                products = repository.getAdvanceFilter(parent_id, catId, featured, "", "")

                products.let {
                    if (products!!.status == 1) {
                        mBinding.rvProduct.show()
                        mBinding.tvEmptyText.hide()
                        setAdapter(products!!.result)
                    } else {
                        mBinding.rvProduct.hide()
                        mBinding.tvEmptyText.show()
                        toast(products!!.message)
                    }
                    //hide
                    this.progress_bars_layout.hide()
                    return@main
                }

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
            override fun onClickCatOnProduct(isCat: Int, catId: Int) {
                if (isCat == 1) {
                    this@AdvancedFilterActivity.catId = catId.toString()
                } else {
                    this@AdvancedFilterActivity.featured = catId.toString()
                }
                categoryData.add(catId)
                callAdvanceFilterApiCall()
            }
        })
        mBinding.rvProduct.adapter = adapter
    }
}
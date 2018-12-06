package com.yoie.com.cobinhoondtestkotlin

import android.app.Application
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import com.yoie.com.cobinhoondtestkotlin.Model.TickerResult
import com.yoie.com.cobinhoondtestkotlin.Model.TradeData
import com.yoie.com.cobinhoondtestkotlin.ViewModel.TradeListAdapter
import com.yoie.com.cobinhoondtestkotlin.WebService.ApiClient
import com.yoie.com.cobinhoondtestkotlin.WebService.ApiResponse
import com.yoie.com.cobinhoondtestkotlin.WebService.NetworkScheduler
import android.support.v7.widget.GridLayoutManager
import android.util.Log


class MainActivity : RxAppCompatActivity() {
    var mDatas = mutableListOf<TradeData>()
    var mRecyclerView: RecyclerView? = null
    var mAdapter: TradeListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView

        var x = "24.5"
        var y = "12.2"
        var z = (x.toDouble()/y.toDouble()).toString()
        Log.d("test", z)
        ApiClient.instance.init()
        refreshData()

    }


    private fun refreshData() {
        ApiClient.instance.service.getPage("v1/market/tickers")
                .compose(NetworkScheduler.compose())
                .bindUntilEvent(this, ActivityEvent.DESTROY)
                .subscribe(object : ApiResponse<TickerResult>(this) {
                    override fun success(data: TickerResult) {
                        val dataTree = data.result as LinkedTreeMap<String, List<LinkedTreeMap<*, *>>>
                        val dataA = dataTree["tickers"]
                        for (item in dataA!!) {
                            val jsonObj = Gson().toJsonTree(item).asJsonObject
                            val temp = Gson().fromJson<TradeData>(jsonObj, TradeData::class.java)
                            mDatas?.add(temp)
                        }

                        if(mDatas?.size!! >= 0)
                            refresh()
                    }

                    override fun failure(statusCode: Int) {
                        Toast.makeText(this@MainActivity, "err1", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    fun refresh() {
        //var layoutManager = LinearLayoutManager(this)
        var layoutManager = GridLayoutManager(this, 2)

        mRecyclerView!!.layoutManager = layoutManager
        mAdapter = TradeListAdapter(mDatas!!)
        mRecyclerView!!.adapter = mAdapter
        mRecyclerView!!.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    }


}

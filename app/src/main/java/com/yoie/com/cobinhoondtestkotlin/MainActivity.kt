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
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import java.net.URI


class MainActivity : RxAppCompatActivity() {
    var mDatas = mutableListOf<TradeData>()
    var mRecyclerView: RecyclerView? = null
    var mAdapter: TradeListAdapter? = null

    val mWs = object : WebSocketClient(URI("wss://ws.cobinhood.com/v2/ws"), Draft_6455()) {
        override fun onMessage(message: String) {
            val obj = JSONObject(message)
            var tempH = obj.getJSONArray("h")
            var tempD = obj.getJSONArray("d")
            if(tempH[2].toString().equals("s") || tempH[2].toString().equals("u")){
                for(i in 0 .. mDatas.size-1){
                    if(tempH[0].toString().contains(mDatas[i].pairID!!)){
                        val x = TradeData().apply {
                            pairID = mDatas[i].pairID
                            timestamp = tempD[0].toString().toDouble()
                            mHighestBid = tempD[1].toString()
                            mLowestAsk = tempD[2].toString()
                            m24hVolume = tempD[3].toString()
                            m24hHigh = tempD[4].toString()
                            m24hLow = tempD[5].toString()
                            m24Open = tempD[6].toString()
                            lastTradePrice = tempD[7].toString()
                        }
                        mDatas.set(i,x)
                        mAdapter!!.notifyDataSetChanged()
                        break
                    }

                }
            }
        }

        override fun onOpen(handshake: ServerHandshake) {
            subscribeAllPairID()
        }

        override fun onClose(code: Int, reason: String, remote: Boolean) {
        }

        override fun onError(ex: Exception) {
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        var layoutManager = GridLayoutManager(this, 2)
        mRecyclerView!!.layoutManager = layoutManager
        mAdapter = TradeListAdapter(mDatas)
        mAdapter!!.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {})
        mRecyclerView!!.adapter = mAdapter
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
                            mDatas.add(temp)
                        }
                        if(mDatas.size >= 0){
                            mAdapter!!.notifyDataSetChanged()
                            object : Thread(){
                                override fun run() {
                                    mWs.connect()
                                }
                            }.start()
                        }
                    }

                    override fun failure(statusCode: Int) {
                        Toast.makeText(this@MainActivity, "err1", Toast.LENGTH_SHORT).show()
                    }
                })
    }
    fun subscribeAllPairID(){
        for(i in 0.. mDatas.size-1){
            val obj = JSONObject().apply {
                put("action", "subscribe")
                put("type", "ticker")
                put("trading_pair_id", mDatas[i].pairID)
            }
            mWs.send(obj.toString())

        }
    }


}

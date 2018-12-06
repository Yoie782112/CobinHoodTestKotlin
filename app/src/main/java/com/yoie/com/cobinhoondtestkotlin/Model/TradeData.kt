package com.yoie.com.cobinhoondtestkotlin.Model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import android.databinding.BaseObservable
import android.databinding.Bindable



class TradeData : BaseObservable() {

    @SerializedName("trading_pair_id")
    @Expose
    @get:Bindable
    var pairID: String? = null
    val ratio: String
        @Bindable get() {
            if(m24Open!!.equals("0"))
                return "0"
            return ((lastTradePrice!!.toDouble()?.minus(m24Open!!.toDouble()) )/m24Open!!.toDouble()).toString()
        }

    @SerializedName("timestamp")
    @Expose
    var timestamp: Double? = null

    @SerializedName("highest_bid")
    @Expose
    private var mHighestBid: String? = null

    @SerializedName("lowest_ask")
    @Expose
    private var mLowestAsk: String? = null

    @SerializedName("24h_volume")
    @Expose
    var m24hVolume: String? = null

    @SerializedName("24h_low")
    @Expose
    var m24hLow: String? = null

    @SerializedName("24h_high")
    @Expose
    var m24hHigh: String? = null

    @SerializedName("24h_open")
    @Expose
    var m24Open: String? = null

    @SerializedName("last_trade_price")
    @Expose
    @get:Bindable
    var lastTradePrice: String? = null

    fun getmHighestBid(): String? {
        return mHighestBid
    }

    fun setmHighestBid(mHighestBid: String) {
        this.mHighestBid = mHighestBid
    }

    fun getmLowestAsk(): String? {
        return mLowestAsk
    }

    fun setmLowestAsk(mLowestAsk: String) {
        this.mLowestAsk = mLowestAsk
    }


}
package com.yoie.com.cobinhoondtestkotlin.Model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import java.text.DecimalFormat

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

class TradeData : BaseObservable() {

    @SerializedName("trading_pair_id")
    @Expose
    @get:Bindable
    var pairID: String? = null
    val ratio: SpannableStringBuilder
        @Bindable get() {
            return if(m24Open!!.toDouble() == 0.0)   SpannableStringBuilder("0.0").apply{setSpan(ForegroundColorSpan(Color.BLACK),0,2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)}
            else{
                var dRatio = (lastTradePrice!!.toDouble()-(m24Open!!.toDouble()) )/m24Open!!.toDouble()
                val df = DecimalFormat("#.##")
                var sRatio = df.format(dRatio).toString()
                if(dRatio == 0.0) SpannableStringBuilder(sRatio).apply{setSpan(ForegroundColorSpan(Color.BLACK),0,sRatio.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)}
                else if(dRatio < 0.0) SpannableStringBuilder(sRatio).apply{setSpan(ForegroundColorSpan(Color.GREEN),0,sRatio.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)}
                else SpannableStringBuilder(sRatio).apply{setSpan(ForegroundColorSpan(Color.RED),0,sRatio.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)}
            }
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
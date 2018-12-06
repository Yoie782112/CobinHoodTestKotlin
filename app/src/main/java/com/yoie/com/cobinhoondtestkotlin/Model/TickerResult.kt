package com.yoie.com.cobinhoondtestkotlin.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TickerResult {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null
        set(trades) {
            field = this.success
        }

    @SerializedName("result")
    @Expose
    var result: Any? = null
}
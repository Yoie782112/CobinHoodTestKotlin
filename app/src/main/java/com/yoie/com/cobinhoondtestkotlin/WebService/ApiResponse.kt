package com.yoie.com.cobinhoondtestkotlin.WebService

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.disposables.Disposable
import java.util.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
abstract class ApiResponse<T>(private val context: Context) : Observer<T> {
    abstract fun success(data: T)
    abstract fun failure(statusCode: Int)

    override fun onSubscribe(d: Disposable) {
        Log.e("Yoie", "onSubscribe: ")

    }

    override fun onNext(t: T) {
        success(t)
        Log.e("Yoie", "onNext: ")

    }

    override fun onComplete() {
        Log.e("Yoie", "onComplete: ")

    }

    override fun onError(e: Throwable) {
        Log.e("Yoie", "onError: ")

    }


}


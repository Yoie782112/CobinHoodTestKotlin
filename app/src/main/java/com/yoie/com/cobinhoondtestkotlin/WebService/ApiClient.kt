package com.yoie.com.cobinhoondtestkotlin.WebService

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient private constructor() {
    lateinit var service: WebAPIService

    private object Holder {
        val INSTANCE = ApiClient()
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }

    fun init() {  //在Application的onCreate中调用一次即可
//        val okHttpClient = OkHttpClient().newBuilder()
//                //输入http连接时的log，也可添加更多的Interceptor
//                .addInterceptor(HttpLoggingInterceptor().setLevel(
//                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
//                        else HttpLoggingInterceptor.Level.NONE
//                ))
//                .build()
//
//        val retrofit = Retrofit.Builder()
//                .baseUrl("https://api.github.com/")   //本文以GitHub API为例
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(okHttpClient)
//                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.cobinhood.com/") //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        service = retrofit.create(WebAPIService::class.java)
    }
}


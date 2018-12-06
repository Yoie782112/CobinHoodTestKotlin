package com.yoie.com.cobinhoondtestkotlin.WebService

import com.yoie.com.cobinhoondtestkotlin.Model.TickerResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface WebAPIService {
    @GET
    fun getPage(@Url url: String): Observable<TickerResult>
}
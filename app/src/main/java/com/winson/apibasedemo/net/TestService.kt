package com.winson.apibasedemo.net

import com.winson.apibasedemo.tools.WxPay
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TestService {

    @GET("/test/pay")
    fun pay(
        @Query("seller_id") seller_id: String,
        @Query("buyer_id") buyer_id: String,
        @Query("total_fee") total_fee: String,
        @Query("out_trade_no") out_trade_no: String,
        @Query("subject") subject: String
    ): Observable<MyResult>

    @GET("/test/app")
    fun app(): Observable<MyResult>


    @GET("/test/wx")
    fun getWxPay(@Query("seller_id") seller_id: String,
                 @Query("buyer_id") buyer_id: String,
                 @Query("total_fee") total_fee: String,
                 @Query("out_trade_no") out_trade_no: String,
                 @Query("subject") subject: String): Observable<WxPay>

}
package com.winson.apibasedemo.net

import io.reactivex.Observable
import retrofit2.http.GET

interface LocalService {

    @GET("/test/say")
    fun say(): Observable<MyResult>

}
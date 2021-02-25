package com.sylko.twitchapi.api

import com.sylko.twitchapi.common.BASE_URL
import com.sylko.twitchapi.pojo.TopListOfData
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @GET("games/top")
    @Headers("Accept: application/vnd.twitchtv.v5+json", "Client-ID: sd4grh0omdj9a31exnpikhrmsu3v46")
    fun loadGames(
            @Query("limit") limit: Int? = 0,
            @Query("offset") offset: Int? = 0
    ): Observable<TopListOfData>

    companion object {
        fun getService(): ApiService {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

}
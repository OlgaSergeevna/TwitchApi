package com.sylko.twitchapi.api

import com.sylko.twitchapi.pojo.TopListOfData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @GET("games/top")
    @Headers("Accept: application/vnd.twitchtv.v5+json", "Client-ID: sd4grh0omdj9a31exnpikhrmsu3v46")
    fun loadGames(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<TopListOfData>

}
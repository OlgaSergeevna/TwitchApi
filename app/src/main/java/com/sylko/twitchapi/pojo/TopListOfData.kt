package com.sylko.twitchapi.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TopListOfData(

    @SerializedName("top")
    @Expose
    val top : List<TopGames>,

    @SerializedName("_total")
    @Expose
    val _total : Int?

)

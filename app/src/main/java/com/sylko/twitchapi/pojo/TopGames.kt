package com.sylko.twitchapi.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TopGames(

    @SerializedName("channels")
    @Expose
    val channels: Int?,

    @SerializedName("viewers")
    @Expose
    val viewers: Int?,

    @SerializedName("game")
    @Expose
    val game: Game
)

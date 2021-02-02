package com.sylko.twitchapi.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("name")
    @Expose
    val name: String?,

    @SerializedName("box")
    @Expose
    val box: BoxImage?,

    @SerializedName("_id")
    @Expose
    val id: Int
)

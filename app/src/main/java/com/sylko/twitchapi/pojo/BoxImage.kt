package com.sylko.twitchapi.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BoxImage(

    @SerializedName("large")
    @Expose
    val large: String?
)

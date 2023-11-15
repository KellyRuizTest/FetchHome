package com.loder.fetchhome.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Items(
    @SerializedName("")
    val items: List<ItemModel>,
) : Serializable

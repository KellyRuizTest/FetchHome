package com.loder.fetchhome.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ItemModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("listId")
    var listId: Int,
    @SerializedName("name")
    var name: String,
) : Serializable

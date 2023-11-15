package com.loder.fetchhome.data.remote

import com.loder.fetchhome.data.model.ItemModel
import retrofit2.Response

interface MainRepository {

    suspend fun getFetchItems(): Response<ItemModel>
}
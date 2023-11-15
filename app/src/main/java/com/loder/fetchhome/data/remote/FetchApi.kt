package com.loder.fetchhome.data.remote

import com.loder.fetchhome.data.model.ItemModel
import com.loder.fetchhome.data.model.Items
import com.loder.fetchhome.util.Constants
import retrofit2.Response
import retrofit2.http.GET

// List all API Calls
interface FetchApi {

    @GET(Constants.RANDOM_URL)
    suspend fun getFetchItems(): Response<List<ItemModel>>
}

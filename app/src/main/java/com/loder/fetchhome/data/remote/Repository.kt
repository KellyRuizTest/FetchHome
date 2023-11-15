package com.loder.fetchhome.data.remote

import javax.inject.Inject

class Repository @Inject constructor(private val api: FetchApi) {

    suspend fun getFetchItem() = api.getFetchItems()
}

package com.loder.fetchhome.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loder.fetchhome.data.model.ItemModel
import com.loder.fetchhome.data.remote.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = "FetchItemViewModel"

@HiltViewModel
class FetchItemViewModel
@Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val itemLiveData = MutableLiveData<List<ItemModel>>()
    private val categoriesCount = MutableLiveData<Int>()

    fun getFetchItems() = viewModelScope.launch {
        val listFiltered = mutableListOf<ItemModel>()
        repository.getFetchItem().let { response ->

            if (response.isSuccessful) {
                val lista = response.body()!!
                // filtering list by empty or null name
                val filterList = lista.filter { it.name != null && !it.name.equals("") }
                // sorting by listId and name (because name is built by "Item id") -> it means sorting by Id
                val bylistId = filterList.sortedWith(compareBy({ it.listId }, { it.id }))
                // post result value List in LiveData
                val cat = lista.groupBy { it.listId }.size
                categoriesCount.postValue(cat)
                itemLiveData.postValue(bylistId)
            } else {
                Log.e(TAG, "Error fetching: ${response.message()}")
            }
        }
    }

    fun observeFetchItem(): LiveData<List<ItemModel>> {
        return itemLiveData
    }

    fun observeCatCount(): LiveData<Int> {
        return categoriesCount
    }

    fun categorizingItem(id: Int): List<ItemModel> {
        val itemListCat = itemLiveData.value?.filter { it.listId == id }
        return itemListCat!!
    }
}

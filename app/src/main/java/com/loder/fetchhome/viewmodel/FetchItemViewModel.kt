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

    fun getFetchItems() = viewModelScope.launch {
        val listFiltered = mutableListOf<ItemModel>()
        repository.getFetchItem().let { response ->

            if (response.isSuccessful) {
                val lista = response.body()!!
                val filterList = lista.filter { it.name != null && !it.name.equals("") }
                val bylistId = filterList.sortedBy { it.listId }
                println(bylistId)
                itemLiveData.postValue(bylistId)
            } else {
                Log.e(TAG, "Error fetching: ${response.message()}")
            }
        }
    }

    fun observeFetchItem(): LiveData<List<ItemModel>> {
        return itemLiveData
    }
}

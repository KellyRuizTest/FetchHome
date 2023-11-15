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

                for (i in lista.indices) {
                    if (!(lista[i].name == null) && !(lista[i].name.equals(""))) {
                        listFiltered.add(lista[i])
                    }
                }
                itemLiveData.postValue(listFiltered)
            } else {
                Log.e(TAG, "Error fetching: ${response.message()}")
            }
        }
    }

    fun observeFetchItem(): LiveData<List<ItemModel>> {
        return itemLiveData
    }
}

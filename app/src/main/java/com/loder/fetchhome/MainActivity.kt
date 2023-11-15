package com.loder.fetchhome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loder.fetchhome.adapter.ItemAdapter
import com.loder.fetchhome.databinding.ActivityMainBinding
import com.loder.fetchhome.viewmodel.FetchItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Adapters
    private lateinit var itemAdapter: ItemAdapter

    // Recyclers
    private lateinit var itemRecycler: RecyclerView

    // viewModels
    private lateinit var viewModel: FetchItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(FetchItemViewModel::class.java)
        viewModel.getFetchItems()

        getListItems()
    }

    private fun getListItems() {
        itemRecycler = binding.rvItems
        itemRecycler.layoutManager = LinearLayoutManager(this)
        itemRecycler.setHasFixedSize(true)

        viewModel.observeFetchItem().observe(this) {
            itemAdapter = ItemAdapter(it)
            itemRecycler.adapter = itemAdapter
        }
    }
}

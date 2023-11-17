package com.loder.fetchhome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.loder.fetchhome.adapter.ItemAdapter
import com.loder.fetchhome.databinding.ActivityMainBinding
import com.loder.fetchhome.databinding.ChoiceChipBinding
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

        itemRecycler = binding.rvItems
        itemRecycler.layoutManager = LinearLayoutManager(this)
        itemRecycler.setHasFixedSize(true)

        getListItems()
        getChipCat()
    }

    private fun getListItems() {
        viewModel.observeFetchItem().observe(this) {
            itemAdapter = ItemAdapter(it)
            itemRecycler.adapter = itemAdapter
        }
    }

    private fun getChipCat() {
        viewModel.observeCatCount().observe(this) {
            createChips(it)
        }
    }

    private fun createChips(num: Int) {
        for (i in 1..num) {
            val chip = ChoiceChipBinding.inflate(layoutInflater).root
            var textName = "Items" + i.toString()
            chip.text = textName
            chip.tag = i
            binding.chipGroup.addView(chip)
        }

        binding.chipGroup.setOnCheckedChangeListener(
            ChipGroup.OnCheckedChangeListener { chipGroup, i ->

                if (i in 1..num) {
                    getListById(i)
                } else {
                    getListItems()
                }
            },
        )
    }

    private fun getListById(tag: Int) {
        val filtered = viewModel.categorizingItem(tag)
        itemAdapter = ItemAdapter(filtered)
        itemRecycler.adapter = itemAdapter
        itemAdapter.notifyDataSetChanged()
    }
}

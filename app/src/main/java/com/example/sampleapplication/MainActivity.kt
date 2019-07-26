package com.example.sampleapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initUI()
    }

    fun initUI() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.dataListResultUIModel.observe(this, Observer { listData ->

            binding.listRv.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
            binding.listRv.adapter = DataListAdapter()
            var adapter = binding.listRv.adapter as DataListAdapter
            listData.let {
                adapter.updateData(listData)
            }
        })

        viewModel.getData()

    }
}

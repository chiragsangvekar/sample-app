package com.example.sampleapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleapplication.databinding.ActivityMainBinding
import com.example.sampleapplication.db.AppDatabase
import com.example.sampleapplication.db.MyData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initUI()
    }

    fun initUI() {
        val dataDao = AppDatabase.get(applicationContext).myDataDao()
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.dataListResultUIModel.observe(this, Observer { listData ->

            binding.listRv.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
            binding.listRv.adapter = DataListAdapter()
            var adapter = binding.listRv.adapter as DataListAdapter
            listData.let {
                adapter.updateData(listData)
            }

        })

        var mainActivityInputFragment = MainActivityInputFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.inputFragment, mainActivityInputFragment)
        fragmentTransaction.commit()

        var mainActivityOutputFragment = MainActivityOutputFragment()
        val fragmentManager_2 = supportFragmentManager
        val fragmentTransaction_2 = fragmentManager_2.beginTransaction()
        fragmentTransaction_2.replace(R.id.outputFragment, mainActivityOutputFragment)
        fragmentTransaction_2.commit()


//        viewModel.getData()

        //add fragment 1

    }

}

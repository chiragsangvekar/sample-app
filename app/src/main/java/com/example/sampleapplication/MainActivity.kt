package com.example.sampleapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleapplication.databinding.ActivityMainBinding
import com.example.sampleapplication.db.AppDatabase
import com.example.sampleapplication.db.MyData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.launch
import java.io.FileOutputStream
import java.net.URL

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

            binding.listRv.layoutManager = GridLayoutManager(this,2) as RecyclerView.LayoutManager?
            binding.listRv.adapter = DataListAdapter(applicationContext)
            var adapter = binding.listRv.adapter as DataListAdapter
            listData.let {
                adapter.updateData(listData)
            }

            launch {
                dataDao.insertAll(transformToDBDAta(listData))
                Log.d("MainActivityTest","Entry: " + dataDao.findData(1))
            }
        })

        viewModel.getData()

    }

    fun transformToDBDAta(data: List<ListDataUIModel>): List<MyData> {
        var myData = ArrayList<MyData>()
        data.forEach {
            myData.add(MyData(
                uId = 0,
                text = it.text,
                data = it.url))
        }
        return myData
    }
}

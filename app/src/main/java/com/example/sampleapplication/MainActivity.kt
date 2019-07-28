package com.example.sampleapplication

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleapplication.db.AppDatabase
import com.example.sampleapplication.db.MyData
//import com.example.sampleapplication.databinding.ActivityMainBinding

import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.experimental.launch
//import kotlinx.coroutines.experimental.launch
import java.io.FileOutputStream
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    var searchInput : String? = null
    lateinit var search_input : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        search_input=findViewById(R.id.editText)
        val dataDao = AppDatabase.get(applicationContext).myDataDao()

//        launch {
//            dataDao.insertAll(transformToDBDAta(listData))
//            Log.d("MainActivityTest","Entry: " + dataDao.findData(1))
//        }
    }

    fun searchQuery(search : String) {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.response.observe(this,Observer {queryResultModel ->
            //start new activity
            val intent = Intent(this,SearchResultsActivity::class.java)
            var gson = Gson()
            var jsonString = gson.toJson(queryResultModel)

            intent.putExtra("query_result",jsonString)
            startActivity(intent)
        })
        viewModel.getData(search)
    }

    fun onSearch(view: View ){
        searchInput = search_input.text.toString()
        searchQuery(searchInput.toString())
    }

//    fun transformToDBDAta(data: List<ListDataUIModel>): List<MyData> {
//        var myData = ArrayList<MyData>()
//        data.forEach {
//            myData.add(
//                MyData(
//                    uId = 0,
//                    text = it.text,
//                    data = it.url)
//            )
//        }
//        return myData
//    }
}

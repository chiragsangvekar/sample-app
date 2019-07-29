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
//import kotlinx.coroutines.experimental.launch
//import kotlinx.coroutines.experimental.launch
import java.io.FileOutputStream
import java.net.URL
import android.R.id.edit
import android.text.method.TextKeyListener.clear
import android.content.SharedPreferences



class MainActivity : AppCompatActivity() , MainActivtyRecyclerAdapter.ItemClickListener{
    override fun onItemClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var viewModel: MainActivityViewModel
    var searchInput : String? = null
    lateinit var search_input : EditText
    private lateinit var binding: com.example.sampleapplication.databinding.ActivityMainBinding
    lateinit var adapter : MainActivtyRecyclerAdapter
    lateinit var imageList : List<ImageListEntry>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        search_input=findViewById(R.id.editText)
        val dataDao = AppDatabase.get(applicationContext).myDataDao()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        launch {
//            dataDao.insertAll(transformToDBDAta(listData))
//            Log.d("MainActivityTest","Entry: " + dataDao.findData(1))
//        }


//        initUI()

        viewModel.downloadedImagesList.observe(this , Observer {
            imageList = it
//            adapter.updateData(imageList)
//            loadFragment(it)
        })
        viewModel.downloadImage("https://www.gstatic.com/webp/gallery3/1.png")
        viewModel.downloadImage("https://www.gstatic.com/webp/gallery/1.jpg")
    }

     fun loadFragment(imagelist : List<ImageListEntry>) {
         Log.d("ImageLoadingLibrary", "loading fragment" )
         val fragmentDemo = supportFragmentManager.findFragmentById(R.id.frameContainer)
         //above part is to determine which fragment is in your frame_container
//        setFragment(fragmentDemo)
         var mainActivityFragment = MainActivityRecyclerViewFragment(imageList,this)
         val fragmentManager = supportFragmentManager
         val fragmentTransaction = fragmentManager.beginTransaction()
         fragmentTransaction.replace(R.id.frameContainer, mainActivityFragment)
         fragmentTransaction.commit()
         binding.frameContainer.visibility = View.VISIBLE
         binding.button.visibility = View.GONE

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
//        searchInput = search_input.text.toString()
//        searchQuery(searchInput.toString())
        loadFragment(imageList)
    }


    fun initUI() {
//        Log.d("ImageLoadingLibrary", "Initializing recyclerView")
//        binding.listRv.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
//        binding.listRv.adapter = MainActivtyRecyclerAdapter(this)
//        adapter = binding.listRv.adapter as MainActivtyRecyclerAdapter
//        adapter.updateData(listDateUIModel)
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

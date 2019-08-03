package com.example.sampleapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import com.example.sampleapplication.databinding.ActivityMainBinding
import com.example.sampleapplication.db.AppDatabase
import com.example.sampleapplication.db.MyData
import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.coroutines.experimental.channels.Send

//import kotlinx.coroutines.experimental.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(R.layout.activity_main)
        initUI()
    }

    fun initUI() {
        val dataDao = AppDatabase.get(applicationContext).myDataDao()
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        // 1
        happyButton.setOnClickListener({
            emotionalFaceView.happinessState = EmotionalFaceView.HAPPY
        })
// 2
        sadButton.setOnClickListener({
            emotionalFaceView.happinessState = EmotionalFaceView.SAD
        })
//        viewModel.dataListResultUIModel.observe(this, Observer { listData ->
//
//            binding.listRv.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
//            binding.listRv.adapter = DataListAdapter()
//            var adapter = binding.listRv.adapter as DataListAdapter
//            listData.let {
//                adapter.updateData(listData)
//            }
//
//            launch {
//                dataDao.insertAll(transformToDBDAta(listData))
//                Log.d("MainActivityTest","Entry: " + dataDao.findData(1))
//            }
//        })

//        viewModel.getData()

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ActivityCallbacks","A1 destroy")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ActivityCallbacks","A1 pause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("ActivityCallbacks","A1 stop")

    }

    override fun onResume() {
        super.onResume()
        Log.d("ActivityCallbacks","A1 resume")

    }

    override fun onStart() {
        super.onStart()
        Log.d("ActivityCallbacks","A1 start")

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

    fun startNewActivity(view : View){
        val intent = Intent(this, Activity2::class.java)
// To pass any data to next activity
        intent.setAction(Intent.ACTION_SEND)
        intent.putExtra("keyIdentifier", "Hello")
// start your next activity
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("ActivityCallbacks","A1 onActivityResult")
        if(resultCode == Activity.RESULT_OK){
            Log.d("ActivityCallbacks","A1 onActivityResult success")
        }

    }
}

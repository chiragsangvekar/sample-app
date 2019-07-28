package com.example.sampleapplication

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleapplication.Network.RetrofitProvider
//import com.example.sampleapplication.databinding.ActivityMainBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import java.io.*
import java.net.URL
import kotlin.text.Charsets.UTF_8

//import com.sun.tools.corba.se.idl.Util.getAbsolutePath




class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val _resultQuery = MutableLiveData<QueryResultModel>()
    val resultQuery: LiveData<QueryResultModel> = _resultQuery
    private  val _response = MutableLiveData<QueryResultModel>()
    val response : LiveData<QueryResultModel> = _response

    val aRetrofitServices by lazy { RetrofitProvider.instance.services }


    fun getData(search : String) {
        aRetrofitServices.getDataList(search).enqueue(object : retrofit2.Callback<QueryResultModel> {
            override fun onFailure(call: Call<QueryResultModel>, t: Throwable) {
                Log.d("RetrofitCall", "failed: " + t.message + " " + t.stackTrace)
            }

            override fun onResponse(call: Call<QueryResultModel>, response: Response<QueryResultModel>) {
                Log.d("RetrofitCall", "success: " + response.body())
                _response.postValue(response.body())
            }
        })
    }

}
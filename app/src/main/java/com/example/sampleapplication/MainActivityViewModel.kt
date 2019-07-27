package com.example.sampleapplication

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleapplication.Network.RetrofitProvider
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import kotlin.text.Charsets.UTF_8

//import com.sun.tools.corba.se.idl.Util.getAbsolutePath




class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val _dataListResultUIModel = MutableLiveData<List<ListDataUIModel>>()
    val dataListResultUIModel: LiveData<List<ListDataUIModel>> = _dataListResultUIModel
    var imageCount = 0;
    val context = getApplication<Application>().applicationContext

    val aRetrofitServices by lazy { RetrofitProvider.instance.services }

    fun getData() {
        aRetrofitServices.getDataList().enqueue(object : retrofit2.Callback<ListDataNwModel> {
            override fun onFailure(call: Call<ListDataNwModel>, t: Throwable) {
                Log.d("RetrofitCall", "failed: " + t.message + " " + t.stackTrace)
                var cachedData = readFileLineByLineUsingForEachLine()
                transformToUIModel(cachedData)
            }

            override fun onResponse(call: Call<ListDataNwModel>, response: Response<ListDataNwModel>) {
                Log.d("RetrofitCall", "success: " + response.body())
                transformToUIModel(response.body())
                saveDataToCache(response.body())
                readFileLineByLineUsingForEachLine()
            }
        })
    }

    fun readFileLineByLineUsingForEachLine() : ListDataNwModel {
        val gson = Gson()
        val bufferedReader: BufferedReader = File(context.cacheDir, "cachedfile.txt").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        var listDataNwModel = gson.fromJson(inputString, ListDataNwModel::class.java)
        Log.d("RetrofitCall", "file content from json : " + inputString)
        return listDataNwModel
    }


    fun saveDataToCache(json : ListDataNwModel?){
        val outputFile = File(context.cacheDir, "cachedfile.txt")
        if (outputFile.exists()) {
            outputFile.delete()
        }
        else {
            outputFile.parentFile?.mkdirs()
        }
        Log.d("RetrofitCall", "cache file created" )

        val gson = Gson()
        val jsonString: String = gson.toJson(json, ListDataNwModel::class.java)

        Log.d("RetrofitCall", "written to cache file" )
    }

    fun transformToUIModel(listData: ListDataNwModel?) {
        var listUIData = ArrayList<ListDataUIModel>()
        listData?.list?.forEach {
            if (it.text != null && it.url != null) {
                listUIData.add(
                    ListDataUIModel(
                        text = it.text,
                        url = it.url
                    )
                )
//                DownloadImage().execute(it.url);
            }
        }
        _dataListResultUIModel.postValue(listUIData)
    }

    inner class DownloadImage : AsyncTask<String, Void, Bitmap>() {
        private val TAG = "DownloadImage"

        private fun downloadImageBitmap(sUrl: String): Bitmap? {
            var bitmap: Bitmap? = null
            try {
                val inputStream = URL(sUrl).openStream()   // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream)       // Decode Bitmap
                inputStream.close()
                Log.d(TAG, "Image Downloaded!")
            } catch (e: Exception) {
                Log.d(TAG, "Exception 1, Something went wrong!")
                e.printStackTrace()
            }

            return bitmap
        }

        override fun doInBackground(vararg params: String): Bitmap? {
            return downloadImageBitmap(params[0])
        }

        override fun onPostExecute(result: Bitmap) {
            saveImage(result)
        }

        fun saveImage(b: Bitmap) {
            val foStream: FileOutputStream

            try {
                foStream =context.openFileOutput(imageCount++.toString(), Context.MODE_PRIVATE)
                b.compress(Bitmap.CompressFormat.PNG, 100, foStream)
                foStream.close()
                Log.d("DownloadImage", "Image saved!")

                val file = context.getFileStreamPath(imageCount.toString())
                val imageFullPath = file.getAbsolutePath()
                Log.d("DownloadImage", "Image saved Path :" + imageFullPath )
            } catch (e: Exception) {
                Log.d("DownloadImage", "Exception 2, Something went wrong!")
                e.printStackTrace()
            }

        }

    }


}
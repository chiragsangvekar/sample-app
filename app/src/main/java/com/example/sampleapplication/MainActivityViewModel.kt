package com.example.sampleapplication

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
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
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Response
import java.io.*
import java.net.URL
import kotlin.text.Charsets.UTF_8

//import com.sun.tools.corba.se.idl.Util.getAbsolutePath




public class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val _resultQuery = MutableLiveData<QueryResultModel>()
    val resultQuery: LiveData<QueryResultModel> = _resultQuery
    private  val _response = MutableLiveData<QueryResultModel>()
    val response : LiveData<QueryResultModel> = _response
    var context = application.applicationContext
     var imageCount : Int= 0
    val aRetrofitServices by lazy { RetrofitProvider.instance.services }

    //
    private var _downloadedImagesList = MutableLiveData<List<ImageListEntry>>()
    var downloadedImagesList :LiveData<List<ImageListEntry>> = _downloadedImagesList
    var imageList : MutableList<ImageListEntry> = ArrayList<ImageListEntry>()
    private lateinit var prefs: SharedPreferences


    init {
        imageCount = getLocalImageCount()
    }

    fun getLocalImageCount() : Int {
        return getImagesFromPrefs().size
    }


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

    fun downloadImage(url : String){
        Log.d("ImageLoadingLibrary", "inside download image ; checking local storage" )
        val localImageList : List<ImageListEntry> = getImagesFromPrefs()
        var localStorageFileName : String = "ImageNotAvailable"
        localImageList.forEach { imageListEntry ->
            if(imageListEntry.imageUrl.equals(url)){
                localStorageFileName = imageListEntry.imageName
                Log.d("ImageLoadingLibrary", "image found in local")
            }
        }
        if(localStorageFileName.equals("ImageNotAvailable")){
            Log.d("ImageLoadingLibrary","Image not available locally ; trying to download")
            DownloadImage().execute(url);
        }
        else{
            imageList.add(ImageListEntry(url,localStorageFileName))
            _downloadedImagesList.postValue(imageList)
        }
    }

     inner class DownloadImage : AsyncTask<String, Void, Bitmap>() {
        private val TAG = "ImageLoadingLibrary"
         lateinit var imageUrl :String
        private fun downloadImageBitmap(sUrl: String): Bitmap? {
            var bitmap: Bitmap? = null
            imageUrl = sUrl
            try {
                val inputStream = URL(sUrl).openStream()   // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream)       // Decode Bitmap
                inputStream.close()
                Log.d(TAG, "image downloaded")

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
            saveImage(context, result, imageCount++.toString(), imageUrl)
        }
    }

    fun saveImage(context: Context, b: Bitmap, imageName: String, imageUrl:String) {
        val foStream: FileOutputStream
        b?.let{
            try {
                foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE)
                b.compress(Bitmap.CompressFormat.PNG, 100, foStream)
                foStream.close()
                Log.d("ImageLoadingLibrary", "image saved")

                //put string in list
                imageList.add(ImageListEntry(imageUrl,imageName))

                //store it to shared prefs
                saveImageUrlsToPrefs()
                _downloadedImagesList.postValue(imageList)

            } catch (e: Exception) {
                Log.d("ImageLoadingLibrary", "Exception 2, Something went wrong!")
                e.printStackTrace()
            }
        }
    }

    fun saveImageUrlsToPrefs(){

        var savedImages = HashMap<String,String>()
        imageList.forEach{
            savedImages.put(it.imageUrl,it.imageName)
        }

        var gson = Gson()
        val hashMapString = gson.toJson(savedImages)
        prefs = context.getSharedPreferences("DownloadedImages", Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("hashUrls", hashMapString).apply()
        Log.d("ImageLoadingLibrary","Image saved to prefs")
    }

    fun getImagesFromPrefs() : List<ImageListEntry>{

//        val settings = context.getSharedPreferences("DownloadedImages", Context.MODE_PRIVATE)
//        settings.edit().clear().commit()
//        Log.d("ImageLoadingLibrary", "SharedPrefs cleared")


        prefs = context.getSharedPreferences("DownloadedImages", Context.MODE_PRIVATE)
        val storedHashMapString = prefs.getString("hashUrls", "FailedToFetchFromPrefs")
        Log.d("ImageLoadingLibrary","HashMap: " + storedHashMapString)
        val type = object : TypeToken<HashMap<String, String>>() {

        }.type

        var list = ArrayList<ImageListEntry>()
        var gson = Gson()
        if(!storedHashMapString.equals("FailedToFetchFromPrefs")){
            val testHashMap: HashMap<String, String> = gson.fromJson(storedHashMapString, type) as HashMap<String, String>

            for ((k, v) in testHashMap) {
                list.add(
                    ImageListEntry(
                        imageUrl = k,
                        imageName = v))
            }

            Log.d("ImageLoadingLibrary","Returning hashmap from prefs: " )
        }
        else{
            Log.d("ImageLoadingLibrary","No hashMap found ; Returning null list : " )
        }

        return list
    }

}


//steps :
// check if image exists in sharedpref
// if present load from local storage
//add it to downloaded images list(as it is used for setting recycler view)
//else download and save
//update shared pref
//add it to downloaded images list(as it is used for setting recycler view)


//shred pref logic
//get shared pref if available
//else create one
//add it to it
//store again
//do it evrytime when you want to search image or after download
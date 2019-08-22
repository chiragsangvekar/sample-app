package com.example.sampleapplication.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleapplication.Model.ImageNwModel
import com.example.sampleapplication.Model.ImageUIModel
import com.example.sampleapplication.Model.Items
import com.example.sampleapplication.Network.RetrofitProvider
import retrofit2.Call
import retrofit2.Response
import android.widget.Toast
import android.R.string
import org.json.JSONObject



class SearchActivityViewModel: ViewModel() {
    private val _dataListResultUIModel = MutableLiveData<ImageUIModel>()
    val dataListResultUIModel: LiveData<ImageUIModel> = _dataListResultUIModel

    val aRetrofitServices by lazy { RetrofitProvider.instance.services }

    fun getData(searchText: String) {
        aRetrofitServices.getDataList(searchText).enqueue(object : retrofit2.Callback<ImageNwModel> {
            override fun onFailure(call: Call<ImageNwModel>, t: Throwable) {
                Log.d("RetrofitCall", "failed: " + t.message + " " + t.stackTrace)
            }

            override fun onResponse(call: Call<ImageNwModel>, response: Response<ImageNwModel>) {
                Log.d("RetrofitCall", "success: " + response.body())
                if(response.body() != null){
                    transformToUIModel(response.body())
                } else {
                    try {
                        val jObjError = JSONObject(response.errorBody()?.string())
                        transformToErrorModel( jObjError.getJSONObject("error").getString("message"))
                        Log.d("RetrofitCall", "errorbody: " + jObjError.getJSONObject("error").getString("message"))

                    } catch (e: Exception) {
                        Log.d("RetrofitCall", "e.message : " + e.message)
                    }

                }

            }
        })
    }

    fun transformToErrorModel(error: String){
        _dataListResultUIModel.postValue(
            ImageUIModel(
                pages = null,
                error = error
            )
        )
    }

    fun transformToUIModel(listData: ImageNwModel?) {

        var modelItems : ArrayList<Items>? =  ArrayList<Items>()
        listData?.pageMaps?.let {
            modelItems = it
        }
        _dataListResultUIModel.postValue(
            ImageUIModel(
                pages = modelItems,
                error = ""
            )
        )
    }
}
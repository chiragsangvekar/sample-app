package com.example.sampleapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleapplication.Network.RetrofitProvider
import retrofit2.Call
import retrofit2.Response


class MainActivityViewModel : ViewModel() {

    private val _dataListResultUIModel = MutableLiveData<List<ListDataUIModel>>()
    val dataListResultUIModel: LiveData<List<ListDataUIModel>> = _dataListResultUIModel

    val aRetrofitServices by lazy { RetrofitProvider.instance.services }

    fun getData() {
        aRetrofitServices.getDataList().enqueue(object : retrofit2.Callback<ListDataNwModel> {
            override fun onFailure(call: Call<ListDataNwModel>, t: Throwable) {
                Log.d("RetrofitCall", "failed: " + t.message + " " + t.stackTrace)
            }

            override fun onResponse(call: Call<ListDataNwModel>, response: Response<ListDataNwModel>) {
                Log.d("RetrofitCall", "success: " + response.body())
                transformToUIModel(response.body())
            }
        })
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
            }
        }
        _dataListResultUIModel.postValue(listUIData)
    }

}
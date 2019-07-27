package com.example.sampleapplication.Network

import com.example.sampleapplication.DataNwModel
import com.example.sampleapplication.ListDataNwModel
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitServices {

    @GET("/v2/5c2443f530000054007a5f3e")
    fun getDataList(): Call<ListDataNwModel>

}
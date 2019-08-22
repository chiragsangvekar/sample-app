package com.example.sampleapplication.Network

import com.example.sampleapplication.Model.ImageNwModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {

    @GET("/customsearch/v1?q=harrypotter&cx=011476162607576381860:ra4vmliv9ti&key=AIzaSyDpMYRjzmp67tQoGDmCk8iun_rY657Lefs")
    fun getDataList(@Query("q") searchText: String): Call<ImageNwModel>

}
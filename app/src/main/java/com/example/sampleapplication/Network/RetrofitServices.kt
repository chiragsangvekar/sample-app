package com.example.sampleapplication.Network

//import com.example.sampleapplication.QueryResultModel
//import com.example.sampleapplication.ListDataNwModel
import com.example.sampleapplication.QueryResultModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServices {

    @GET("/customsearch/v1?cx=011476162607576381860:ra4vmliv9ti&key=AIzaSyCWf9BzVYrVhKmC5uKV48jia7Kr54mWmjY")
    fun getDataList(@Query("q") search: String): Call<QueryResultModel>

}
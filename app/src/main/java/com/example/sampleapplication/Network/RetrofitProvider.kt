package com.example.sampleapplication.Network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider private constructor(){
    val services: RetrofitServices

    init {
        services = defaultRetrofitClient.create(RetrofitServices::class.java)
    }

    private object Holder {
        val INSTANCE = RetrofitProvider()
    }

    companion object {
        val BASE_URL = "http://www.mocky.io"
        val instance: RetrofitProvider by lazy { Holder.INSTANCE }
        val defaultRetrofitClient: Retrofit = createRetrofitClient()

        private fun createRetrofitClient() =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }
}
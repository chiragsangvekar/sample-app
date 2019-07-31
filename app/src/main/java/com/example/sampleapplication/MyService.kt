package com.example.sampleapplication

import android.app.IntentService
import android.content.Intent
import android.util.Log

class MyService : IntentService(MyService::class.simpleName){
    override fun onHandleIntent(workIntent: Intent) {
        // Gets data from the incoming Intent

        val bundle = workIntent.getBundleExtra("IntentBundle")
        var input  = bundle.getParcelable<ServiceIntent>("ServiceIntent") as ServiceIntent
        Log.d("MyService","input string : "+ input.toString())
        var cnt : Int = 0
        while(cnt < 30){
            Thread.sleep(5000)
            Log.d("MyService","sleeping")
            cnt++
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyService","Destroying Service")
    }
}



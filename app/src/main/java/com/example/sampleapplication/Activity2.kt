package com.example.sampleapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

class Activity2: AppCompatActivity() {
    var match : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)
        Log.d("ActivityCallbacks","A2 create")

        if(intent.action.equals(Intent.ACTION_SEND)) {
                match = true
            Log.d("ActivityCallbacks","A2 match = true")
            }

    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("ActivityCallbacks","A2 destroy")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ActivityCallbacks","A2 pause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("ActivityCallbacks","A2 stop")

    }

    override fun onResume() {
        super.onResume()
        Log.d("ActivityCallbacks","A2 resume")

    }

    override fun onStart() {
        super.onStart()
        Log.d("ActivityCallbacks","A2 start")

    }

    fun sendToPreviousActivity(view : View){
        val resultIntent = Intent()
        Log.d("ActivityCallbacks","A2 sendToPreviousActivity")
// To pass any data to next activity
// start your next activit
        setResult(Activity.RESULT_OK, resultIntent)
        finish()

    }
}
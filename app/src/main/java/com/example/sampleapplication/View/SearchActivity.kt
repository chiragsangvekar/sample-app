package com.example.sampleapplication.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.search_activity.*
import android.content.Intent
import android.widget.Toast
import com.example.sampleapplication.Constants
import com.example.sampleapplication.R


class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)
        search_btn.setOnClickListener {
            val text = search_et.text.toString()
            if (text.isNullOrEmpty()) {
                Toast.makeText(this, "Enter the text!", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(Constants.SEARCHED_TEXT, text)
                startActivity(intent)
            }
        }
    }
}
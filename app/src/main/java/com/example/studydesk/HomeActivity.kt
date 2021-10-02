package com.example.studydesk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnSearch = findViewById<Button>(R.id.btSearch)
        btnSearch.setOnClickListener {
            startActivity(Intent(this, SearchFileActivity::class.java))
        }
    }
}
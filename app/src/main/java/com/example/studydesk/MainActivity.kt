package com.example.studydesk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upload_documents)

        val button = findViewById<Button>(R.id.upload_button);


        button.setOnClickListener {

            val toast = Toast.makeText(this, "Documento creado correctamente", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP,0,0);
            toast.show();
        }
    }
}
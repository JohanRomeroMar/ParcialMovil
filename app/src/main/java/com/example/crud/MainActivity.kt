package com.example.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
    }

    private fun setup(){
        val buttonCreate: Button = findViewById(R.id.btn_CREATE)
        val buttonRead: Button = findViewById(R.id.btn_READ)

        buttonCreate.setOnClickListener {
            val createIntent = Intent(this, Create::class.java).apply {  }
            startActivity(createIntent)
        }

        buttonRead.setOnClickListener {
            val readIntent = Intent(this, Read::class.java).apply {  }
            startActivity(readIntent)
        }
    }
}
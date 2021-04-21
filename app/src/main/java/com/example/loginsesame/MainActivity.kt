package com.example.loginsesame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openCreateActivity()
    }
    private fun openCreateActivity()
    {
        val intentCreateStartUp = Intent(this@MainActivity, CreateStartUp::class.java)
        this@MainActivity.startActivity(intentCreateStartUp)
    }
}

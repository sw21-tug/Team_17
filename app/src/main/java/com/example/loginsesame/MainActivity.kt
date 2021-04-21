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

    // Account creation
    // TODO: If question account is created
    private fun openCreateActivity()
    {
        val intentCreateStartUp = Intent(this@MainActivity, CreateStartUp::class.java)
        this@MainActivity.startActivity(intentCreateStartUp)
    }
}

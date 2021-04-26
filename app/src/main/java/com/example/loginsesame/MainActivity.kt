package com.example.loginsesame

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check if user exists
            //yes -> open Login Activity
            //no -> open create activity

        //openCreateActivity()
        //call Login Activity on Startup
        openLoginActivity()
    }

    // Account creation
    // TODO: If question account is created
    private fun openCreateActivity()
    {
        val intentCreateStartUp = Intent(this@MainActivity, CreateStartUp::class.java)
        this@MainActivity.startActivity(intentCreateStartUp)
    }

    private fun openLoginActivity() {
        val i = Intent(this@MainActivity, LoginActivity::class.java)
        this@MainActivity.startActivity(i)
    }
}


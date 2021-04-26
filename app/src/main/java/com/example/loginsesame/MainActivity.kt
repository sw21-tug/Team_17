package com.example.loginsesame

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.loginsesame.data.User
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.helper.LogTag
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    val logTag = LogTag()
    var isLoggedIn = false //state if user is logged into the app

    private lateinit var db: UserDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isLoggedIn = intent.getBooleanExtra("isLoggedIn", false)

        db = UserDatabase.initDb(this)
        userDao = db.getUserDao()


        var userExists = false

        GlobalScope.launch {
            if(userDao.getAllUsers().isNotEmpty())
            {
                userExists = true
            }
        }

        //check if user exists
        //yes -> open Login Activity
        //no -> open create activity

        Log.d(logTag.LOG_MAIN, "is Logged in = " + isLoggedIn)
        if(!isLoggedIn && userExists){
            
            openLoginActivity()
        }

        if (!isLoggedIn && !userExists){
            //create new user and automatically login
            openCreateActivity()
        }

    }

    // Account creation
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
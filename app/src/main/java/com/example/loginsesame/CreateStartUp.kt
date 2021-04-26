package com.example.loginsesame

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.loginsesame.data.User
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.helper.LogTag
import kotlinx.coroutines.*


class CreateStartUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        val logTag = LogTag()

        val db = UserDatabase.initDb(this)
        val userDao = db.getUserDao()

        val okButton = findViewById<Button>(R.id.okButton)
        okButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.email).text.toString()
            val username = findViewById<EditText>(R.id.username).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()

            val user = User(null, email, username, password)

            userDao.insertUser(user)

            Log.d(logTag.LOG_STARTUP, "randomUsername")
            Log.d(logTag.LOG_STARTUP, "randomPassword")
            Log.d(logTag.LOG_STARTUP, "randomEmail")

            val intentMain = Intent(this@CreateStartUp, MainActivity::class.java)
            startActivity(intentMain)
        }

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            Log.d(logTag.LOG_STARTUP, "cancelButton")
            val intentMain = Intent(this@CreateStartUp, MainActivity::class.java)
            startActivity(intentMain)
        }

    }

}
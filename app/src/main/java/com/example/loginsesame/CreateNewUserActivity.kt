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


class CreateNewUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_user)

        val logTag = LogTag()

        val db = UserDatabase.initDb(this)
        val userDao = db.getUserDao()

        val okButton = findViewById<Button>(R.id.btnOk)
        val cancelButton = findViewById<Button>(R.id.btnCancel)

        okButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.etEmail).text.toString()
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            val user = User(null, email, username, password)

            userDao.insertUser(user)

            Log.d(logTag.LOG_STARTUP, "randomUsername")
            Log.d(logTag.LOG_STARTUP, "randomPassword")
            Log.d(logTag.LOG_STARTUP, "randomEmail")

            val intentMain = Intent(this@CreateNewUserActivity, MainActivity::class.java)
            intentMain.putExtra("isLoggedIn", true)
            startActivity(intentMain)
        }

        cancelButton.setOnClickListener {
            Log.d(logTag.LOG_STARTUP, "cancelButton")
            val intentMain = Intent(this@CreateNewUserActivity, MainActivity::class.java)
            startActivity(intentMain)
        }

    }

}
package com.example.loginsesame

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loginsesame.data.User
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.data.UserRepository
import com.example.loginsesame.factories.CreateViewModelFactory
import com.example.loginsesame.helper.LogTag
import kotlinx.coroutines.*


class CreateStartUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        val logTag = LogTag()

        val db = UserDatabase.initDb(this)
        val repo = UserRepository(db.getUserDao(), db.getVaultEntryDao())

        val okButton = findViewById<Button>(R.id.okButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val viewModel =
            ViewModelProvider(this, CreateViewModelFactory(repo)).get(CreateViewModel::class.java)

        okButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.email).text.toString()
            val username = findViewById<EditText>(R.id.username).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()

            val user = User(null, email, username, password)
            viewModel.addUser(user)


            Log.d(logTag.LOG_STARTUP, "randomUsername")
            Log.d(logTag.LOG_STARTUP, "randomPassword")
            Log.d(logTag.LOG_STARTUP, "randomEmail")

            val intentMain = Intent(this@CreateStartUp, MainActivity::class.java)
            intentMain.putExtra("isLoggedIn", true)
            startActivity(intentMain)
        }

        cancelButton.setOnClickListener {
            Log.d(logTag.LOG_STARTUP, "cancelButton")
            val intentMain = Intent(this@CreateStartUp, MainActivity::class.java)
            startActivity(intentMain)
        }

    }

}
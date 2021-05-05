package com.example.loginsesame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.example.loginsesame.data.User
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.helper.LogTag

class LoginActivity : AppCompatActivity() {
    val logTag = LogTag()
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // setOnClickListeners for the Buttons
        val etInputPassword = findViewById<EditText>(R.id.etInputPassword)
        val btnInputPasswordOK = findViewById<Button>(R.id.btnInputPasswordOK)
        val btnInputPasswordCancel = findViewById<Button>(R.id.btnInputPasswordCancel)
        val logTag = LogTag()


        db = UserDatabase.initDb(this)
        userDao = db.getUserDao()

        btnInputPasswordOK.setOnClickListener {
            Log.d(logTag.LOG_LOGIN, "btnInputPasswordOK")

            //check if more than one user exists
            val createdUsers = userDao.getAllUsers()
            if (createdUsers.size != 1) {
                Log.e(logTag.LOG_LOGIN, "Invalid number of Users in Database " + createdUsers.size)
                Toast.makeText(this@LoginActivity, "Too many Users in Database, check Support for help", Toast.LENGTH_SHORT).show()
            } else {
                val mainUser = createdUsers[0]
                Log.d(logTag.LOG_LOGIN, mainUser.Password)
                if (etInputPassword.text.toString() == mainUser.Password) {
                    Log.d(logTag.LOG_LOGIN, "Password Correct")
                    //open account list

                    openAccontList()
                } else {
                    Log.d(logTag.LOG_LOGIN, "Incorrect Password")
                    Toast.makeText(this@LoginActivity, "Incorrect Password", Toast.LENGTH_SHORT).show()
                    etInputPassword.text.clear()
                }
            }

        }

        btnInputPasswordCancel.setOnClickListener {
            Log.d(logTag.LOG_LOGIN, "btnInputPasswordCancel")
        }
    }

    private fun openMainActivity() {
        val i = Intent(this@LoginActivity, MainActivity::class.java)
        i.putExtra("isLoggedIn", true)
        this@LoginActivity.startActivity(i)
    }

    private fun openAccontList(){
        val i = Intent(this@LoginActivity, AccountList::class.java)
        this@LoginActivity.startActivity(i)
    }

    //remove back button ability
    override fun onBackPressed() {
        Log.d(logTag.LOG_LOGIN, "Back-Button Pressed With No Action")
    }
}
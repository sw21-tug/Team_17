package com.example.loginsesame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.loginsesame.data.User
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.data.UserRepository
import com.example.loginsesame.helper.LogTag
import kotlinx.coroutines.*

class EditVaultEntry : AppCompatActivity() {
    val logTag = LogTag()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_vault_entry)

        Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "We are Here")

        //daten mitbekommen
        //isLoggedIn = intent.getBooleanExtra("isLoggedIn", false) //integerExtra

    }


}
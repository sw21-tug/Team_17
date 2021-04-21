package com.example.loginsesame

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.view.View
import com.example.loginsesame.helper.LogTag


class CreateStartUp : AppCompatActivity() {
    val logTag = LogTag()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        val logTag = LogTag()

        val okButton = findViewById<Button>(R.id.okButton)
        okButton.setOnClickListener {
            // TODO Account safe to data base
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
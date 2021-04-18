package com.example.loginsesame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.loginsesame.helper.LogTag

class LoginActivity : AppCompatActivity() {
    val logTag = LogTag()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // setOnClickListeners for the Buttons
        val btnInputPasswordOK = findViewById<Button>(R.id.btnInputPasswordOK)
        val btnInputPasswordCancel = findViewById<Button>(R.id.btnInputPasswordCancel)
        val logTag = LogTag()

        btnInputPasswordOK.setOnClickListener {
            // TODO: LS-007-A This is a stub implementation
            Log.d(logTag.LOG_LOGIN, "btnInputPasswordOK")
            //Toast.makeText(this@MainActivity, "You clicked Ok.", Toast.LENGTH_SHORT).show()
        }

        btnInputPasswordCancel.setOnClickListener {
            // TODO: LS-007-A This is a stub implementation
            Log.d(logTag.LOG_LOGIN, "btnInputPasswordCancel")
            //Toast.makeText(this@MainActivity, "You clicked Cancel.", Toast.LENGTH_SHORT).show()
        }
    }

    //remove back button ability
    override fun onBackPressed() {
        Log.d(logTag.LOG_LOGIN, "Back-Button Pressed With No Action")
    }
}
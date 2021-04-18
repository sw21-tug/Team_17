package com.example.loginsesame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.loginsesame.helper.LogTag

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setOnClickListeners for the Buttons
        val btnInputPasswordOK = findViewById<Button>(R.id.btnInputPasswordOK)
        val btnInputPasswordCancel = findViewById<Button>(R.id.btnInputPasswordCancel)
        val logTag = LogTag()

        btnInputPasswordOK.setOnClickListener {
            // TODO: LS-007-A This is a stub implementation
            Log.d(logTag.LOG_MAIN, "btnInputPasswordOK")
            //Toast.makeText(this@MainActivity, "You clicked Ok.", Toast.LENGTH_SHORT).show()
        }

        btnInputPasswordCancel.setOnClickListener {
            // TODO: LS-007-B This is a stub implementation
            Log.d(logTag.LOG_MAIN, "btnInputPasswordCancel")
            //Toast.makeText(this@MainActivity, "You clicked Cancel.", Toast.LENGTH_SHORT).show()
        }


    }


}
package com.example.loginsesame

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.view.View


class CreateStartUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        val okButton = findViewById<Button>(R.id.okButton)
        okButton.setOnClickListener {
            // TODO Account safe to data base
            val intentMain = Intent(this@CreateStartUp, MainActivity::class.java)
            startActivity(intentMain)
        }

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            val intentMain = Intent(this@CreateStartUp, MainActivity::class.java)
            startActivity(intentMain)
        }

    }

}
package com.example.loginsesame

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.data.VaultEntry
import com.example.loginsesame.helper.LogTag

class CreateVaultEntry : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_create_vault_entry)

            val logTag = LogTag()

            val db = UserDatabase.initDb(this)
            val vaultEntryDao = db.getVaultEntryDao()

            val btnVaultSave = findViewById<Button>(R.id.btnVaultSave)
            btnVaultSave.setOnClickListener {
                Log.d(logTag.LOG_OVERVIEW, "createButtonClicked")
                val entryname = findViewById<EditText>(R.id.vaultnameEntry).text.toString()
                val url = findViewById<EditText>(R.id.vaultURL).text.toString()
                val username = findViewById<EditText>(R.id.vaultUsername).text.toString()
                val password = findViewById<EditText>(R.id.vaultPassword).text.toString()

                if (entryname.isEmpty() && url.isEmpty()) {
                    Toast.makeText(this, "@string/url_or_entryname", Toast.LENGTH_LONG).show()
                } else {
                    val vaultentry = VaultEntry(0, entryname, url, username, password)
                    vaultEntryDao.add(vaultentry)

                    Log.d(logTag.LOG_OVERVIEW, "randomEntryName")
                    Log.d(logTag.LOG_OVERVIEW, "randomUrl")
                    Log.d(logTag.LOG_OVERVIEW, "randomUsername")
                    Log.d(logTag.LOG_OVERVIEW, "randomPassword")

                    val intentMain = Intent(this@CreateVaultEntry, MainActivity::class.java)
                    intentMain.putExtra("isLoggedIn", true)
                    startActivity(intentMain)
                }
            }

            val cancelButton = findViewById<Button>(R.id.btnVaultCancel)
            cancelButton.setOnClickListener {
                Log.d(logTag.LOG_OVERVIEW, "cancelButton")
                val intentMain = Intent(this@CreateVaultEntry, MainActivity::class.java)
                startActivity(intentMain)
            }

        }
}
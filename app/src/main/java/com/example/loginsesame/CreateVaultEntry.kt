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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateVaultEntry : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_create_vault_entry)

            val logTag = LogTag()

            val db = UserDatabase.initDb(this)
            val vaultEntryDao = db.getVaultEntryDao()

            val btnVaultSave = findViewById<Button>(R.id.btnVaultSave)
            btnVaultSave.setOnClickListener {
                Log.d(logTag.LOG_CREATE_VAULT_ENTRY, "saveButtonClicked")
                val entryName = findViewById<EditText>(R.id.vaultnameEntry).text.toString()
                val url = findViewById<EditText>(R.id.vaultURL).text.toString()
                val username = findViewById<EditText>(R.id.vaultUsername).text.toString()
                val password = findViewById<EditText>(R.id.vaultPassword).text.toString()

                if (entryName.isEmpty() && url.isEmpty())
                {
                    Log.d(logTag.LOG_CREATE_VAULT_ENTRY, "incorrectData")
                    Toast.makeText(this, R.string.url_or_entryname, Toast.LENGTH_LONG).show()
                }
                else
                {
                    val vaultEntry = VaultEntry(0, entryName, url, username, password)
                    GlobalScope.launch {
                        vaultEntryDao.add(vaultEntry)
                    }

                    Log.d(logTag.LOG_CREATE_VAULT_ENTRY, "randomEntryName")
                    Log.d(logTag.LOG_CREATE_VAULT_ENTRY, "randomUrl")
                    Log.d(logTag.LOG_CREATE_VAULT_ENTRY, "randomUsername")
                    Log.d(logTag.LOG_CREATE_VAULT_ENTRY, "randomPassword")

                    openMainActivity()
                }
            }

            val cancelButton = findViewById<Button>(R.id.btnVaultCancel)
            cancelButton.setOnClickListener {
                Log.d(logTag.LOG_CREATE_VAULT_ENTRY, "cancelButton")

                openMainActivity()
            }

        }

    private fun openMainActivity() {
        val i = Intent(this@CreateVaultEntry, MainActivity::class.java)
        i.putExtra("isLoggedIn", true);
        this@CreateVaultEntry.startActivity(i)
    }
}

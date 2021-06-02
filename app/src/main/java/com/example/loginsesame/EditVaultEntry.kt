package com.example.loginsesame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.data.VaultEntry
import com.example.loginsesame.helper.LogTag
import kotlinx.coroutines.*

class EditVaultEntry : AppCompatActivity() {
    private val logTag = LogTag()

    private lateinit var account : VaultEntry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_vault_entry)

        setTitle(R.string.vault_edit_title)
        val accountId = intent.getIntExtra("accountId", 0)


        Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "We are Here $accountId")

        val db = UserDatabase.initDb(this)
        val vaultEntryDao = db.getVaultEntryDao()

        account = vaultEntryDao.getVaultData(accountId)

        findViewById<EditText>(R.id.vaultnameEntry).setText(account.Name)
        findViewById<EditText>(R.id.vaultURL).setText(account.url)
        findViewById<EditText>(R.id.vaultUsername).setText(account.username)
        findViewById<EditText>(R.id.vaultPassword).setText(account.Password)

        Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "set entryname: " + account.Name)
        Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "set url: " + account.url)
        Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "set username: " + account.username)
        Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "set password: " + account.Password)


        val cancelButton = findViewById<Button>(R.id.btnVaultCancel)
        cancelButton.setOnClickListener {
            Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "cancelButton")

            if (checkEntryChanges()) {
                alertDialogDiscardChanges()
            }
            else {
                openMainActivity()
            }
        }

        val btnVaultSave = findViewById<Button>(R.id.btnVaultSave)

        btnVaultSave.setOnClickListener {
            Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "btnVaultSave")

            val entryName = findViewById<EditText>(R.id.vaultnameEntry).text.toString()
            val url = findViewById<EditText>(R.id.vaultURL).text.toString()
            val username = findViewById<EditText>(R.id.vaultUsername).text.toString()
            val password = findViewById<EditText>(R.id.vaultPassword).text.toString()

            val newAccount = VaultEntry(Id=accountId, Name=entryName, username=username, url=url, Password=password)

            if (newAccount.Password != "")
            {
                GlobalScope.launch {
                    vaultEntryDao.updateVaultEntry(newAccount)
                }
                openMainActivity()

            } else {
                alertDialogNoPassword()
            }
        }
    }

    override fun onBackPressed() {

        Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "backPressed")

        if (checkEntryChanges()) {
            alertDialogDiscardChanges()
        }
        else {
            openMainActivity()
        }
    }

    private fun checkEntryChanges(): Boolean {
        val newEntryname = findViewById<EditText>(R.id.vaultnameEntry).text.toString()
        val newUrl = findViewById<EditText>(R.id.vaultURL).text.toString()
        val newUsername = findViewById<EditText>(R.id.vaultUsername).text.toString()
        val newPassword = findViewById<EditText>(R.id.vaultPassword).text.toString()

        Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "new entryname: $newEntryname")
        Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "new url: $newUrl")
        Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "new username: $newUsername")
        Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "new password: $newPassword")


        return if (account.Name != newEntryname || account.url != newUrl || account.username !=
            newUsername || account.Password != newPassword) {
            Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "Values changed")
            true
        } else {
            Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "Values didn't change")
            false
        }
    }

    private fun alertDialogDiscardChanges() {

        val alert = AlertDialog.Builder(this)
        alert.setTitle(R.string.vault_cancel_changes_alert_title)
        alert.setMessage(R.string.vault_cancel_changes_alert)

        alert.setPositiveButton(R.string.response_positive) { _, _ ->
            Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "alertDialogDiscardChanges Answer: Yes")

            openMainActivity()
        }

        alert.setNegativeButton(R.string.response_negative) { _, _ ->
            Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "alertDialogDiscardChanges Answer: No")
        }
        alert.show()
    }

    private fun alertDialogNoPassword() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle(R.string.vault_save_changes_alert_title)
        alert.setMessage(R.string.vault_save_changes_alert)
        Log.d(logTag.LOG_EDIT_VAULT_ENTRY, "No Password entered")

        alert.show()
    }


    private fun openMainActivity() {
        val i = Intent(this@EditVaultEntry, MainActivity::class.java)
        i.putExtra("isLoggedIn", true)
        this@EditVaultEntry.startActivity(i)
    }
}

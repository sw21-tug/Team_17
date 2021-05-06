package com.example.loginsesame

import android.accounts.Account
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginsesame.RecyclerViewAdapter.RecyclerAdapter
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.data.VaultEntryDao
import kotlinx.android.synthetic.main.activity_password_overview.*

lateinit var accountAdapter : RecyclerAdapter


class AccountList : AppCompatActivity() {

    private lateinit var vaultEntryDao: VaultEntryDao
    private lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_overview)

        db = UserDatabase.initDb(this)
        vaultEntryDao = db.getVaultEntryDao()

        accountAdapter = RecyclerAdapter(mutableListOf())


        rvAccounts.adapter = accountAdapter
        rvAccounts.layoutManager = LinearLayoutManager(this)


        // Inserts Test Values
        for (entry in vaultEntryDao.allEntrys()) {
            var acc = account(entry.Name, entry.username)
            accountAdapter.addAccount(acc)
        }

        val showAllSavedAccounts = findViewById<Button>(R.id.showAllSavedAccounts)
        showAllSavedAccounts.setOnClickListener {
            showAllSavedAccounts
        }
    }

    private fun openShowAccountList() {
        val intentAccountList = Intent(this@AccountList, AccountList::class.java)
        this@AccountList.startActivity(intentAccountList)
    }
}
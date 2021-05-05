package com.example.loginsesame

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginsesame.RecyclerViewAdapter.RecyclerAdapter
import com.example.loginsesame.helper.LogTag
import kotlinx.android.synthetic.main.activity_password_overview.*

lateinit var accountAdapter : RecyclerAdapter

class AccountList : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_overview)

        accountAdapter = RecyclerAdapter(mutableListOf())

        rvAccounts.adapter = accountAdapter
        rvAccounts.layoutManager = LinearLayoutManager(this)


        // Inserts Test Values
        val new_account = account("test@mail.com", "Max Musterman")
        accountAdapter.addAccount(new_account)

        btnAddAccount.setOnClickListener{
            val logTag  = LogTag()
            Log.d(logTag.LOG_OVERVIEW, "btnAddAccountOK")

            val intentCreateVaultEntry = Intent(this@AccountList, CreateVaultEntry::class.java)
            startActivity(intentCreateVaultEntry)
        }
    }




}
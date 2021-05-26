package com.example.loginsesame

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginsesame.data.*
import com.example.loginsesame.factories.MainViewModelFactory
import com.example.loginsesame.helper.LogTag
import com.example.loginsesame.recyclerViewAdapter.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*


lateinit var accountAdapter: RecyclerAdapter

class MainActivity : AppCompatActivity() {

    private val logTag = LogTag()
    var isLoggedIn = false //state if user is logged into the app

    private lateinit var db: UserDatabase
    private lateinit var userDao: UserDao
    private lateinit var vaultEntryDao: VaultEntryDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale()
        setContentView(R.layout.activity_main)
        isLoggedIn = intent.getBooleanExtra("isLoggedIn", false)

        val db = UserDatabase.initDb(this)
        val repository = UserRepository(db.getUserDao(), db.getVaultEntryDao())
        val viewModel =
            ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)


        viewModel.users.observe(this, {
            Log.d(logTag.LOG_MAIN, it.size.toString())
            if (it.isNotEmpty() && !isLoggedIn) {
                openLoginActivity()
            } else if (it.isEmpty() && !isLoggedIn) {
                openCreateActivity()
            }
        })

        viewModel.entries.observe(this, {
            for (entry in it) {
                var acc = Account(entry.Name, entry.username)
                accountAdapter.addAccount(acc)
            }
        })

        accountAdapter = RecyclerAdapter(mutableListOf())


        rvAccounts.adapter = accountAdapter
        rvAccounts.layoutManager = LinearLayoutManager(this)

        btnAddAccount.setOnClickListener {
            val logTag = LogTag()
            Log.d(logTag.LOG_MAIN, "btnAddAccountOK")

            val intentCreateVaultEntry = Intent(this@MainActivity, CreateVaultEntry::class.java)
            startActivity(intentCreateVaultEntry)
            finish()
        }

    }


    // Account creation
    private fun openCreateActivity() {
        val intentCreateStartUp = Intent(this@MainActivity, CreateNewUserActivity::class.java)
        this@MainActivity.startActivity(intentCreateStartUp)
    }

    private fun openLoginActivity() {
        val i = Intent(this@MainActivity, LoginActivity::class.java)
        this@MainActivity.startActivity(i)
    }

    //remove back button ability
    override fun onBackPressed() {
        Log.d(logTag.LOG_LOGIN, "Back-Button Pressed With No Action")
    }

    //create menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    //create menu buttons
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val switchLanguage = item.itemId

        if (switchLanguage == R.id.changeLanguage) {
            showChangeLanguageDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showChangeLanguageDialog(){
        val adb = AlertDialog.Builder(this)
        val items = arrayOf<CharSequence>(
            getString(R.string.language_en),
            getString(R.string.language_ru)
        )

        adb.setSingleChoiceItems(items, -1, DialogInterface.OnClickListener { _, arg1 ->
            if (arg1 == 0)
                setLanguage("en")
            else if (arg1 == 1)
                setLanguage("ru")
            //add more languages if needed
        })
        adb.setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
            //refresh application screen
            recreate()
        })
        adb.setTitle(getString(R.string.language_chooser_title))
        adb.show()


    }

    private fun setLanguage(newLang: String){
        val locale = Locale(newLang)
        Locale.setDefault(locale)
        val config = Configuration()
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", newLang)
        editor.apply()
    }

    private fun loadLocale() {
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "en")
        if (language != null) {
            setLanguage(language)
        }

    }
}
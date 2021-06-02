package com.example.loginsesame

import android.app.Activity
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.loginsesame.data.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep

class TestSearch {
    private lateinit var vaultEntryDao: VaultEntryDao
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase
    private var currentActivity: Activity? = null

    @get:Rule
    var rule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun initDb() {
        Intents.init()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = UserDatabase.initDb(context)
        userDao = db.getUserDao()
        userDao.deleteAllUsers()
        vaultEntryDao = db.getVaultEntryDao()
        vaultEntryDao.deleteAllEntries()
        GlobalScope.launch {
            val entity1 = VaultEntry(1, "thomas", "user_a", "url", "password")
            vaultEntryDao.add(entity1)
            val entity2 = VaultEntry(2, "tobias", "user_b", "url", "password")
            vaultEntryDao.add(entity2)
            val entity3 = VaultEntry(3, "bernhard", "user_c", "url", "password")
            vaultEntryDao.add(entity3)
            val entity4 = VaultEntry(4, "christina", "user_d", "url", "password")
            vaultEntryDao.add(entity4)
            val entity5 = VaultEntry(5, "lukas", "user_e", "url", "password")
            vaultEntryDao.add(entity5)
            val entity6 = VaultEntry(6, "johannes", "user_e", "url", "password")
            vaultEntryDao.add(entity6)
        }
    }

    @After
    fun cleanup() {
        userDao.deleteAllUsers()
        vaultEntryDao.deleteAllEntries()
        Intents.release()
    }

    @Test
    fun testSearchButtonClickable() {

        //create new account
        Espresso.onView(ViewMatchers.withId(R.id.etUsername)).perform(ViewActions.typeText("randomUsername"))
        Espresso.onView(ViewMatchers.withId(R.id.etPassword)).perform(ViewActions.typeText("randomPassword"))
        Espresso.onView(ViewMatchers.withId(R.id.etEmail)).perform(ViewActions.typeText("randomE-Mail"))

        //closing keyboard to press ok Button
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.btnOk)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.search_button)).perform(ViewActions.click())
    }

    @Test
    fun testSearchTextIsShown() {

        //create new account
        Espresso.onView(ViewMatchers.withId(R.id.etUsername)).perform(ViewActions.typeText("randomUsername"))
        Espresso.onView(ViewMatchers.withId(R.id.etPassword)).perform(ViewActions.typeText("randomPassword"))
        Espresso.onView(ViewMatchers.withId(R.id.etEmail)).perform(ViewActions.typeText("randomE-Mail"))

        //closing keyboard to press ok Button
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.btnOk)).perform(ViewActions.click())

        // clicking on the search and search for character t
        Espresso.onView(ViewMatchers.withId(R.id.search_button)).perform(ViewActions.click())
        sleep(1000)
        Espresso.onView(ViewMatchers.withId(com.google.android.material.R.id.search_src_text)).perform(typeText("t"))
        sleep(1000)

    }
}
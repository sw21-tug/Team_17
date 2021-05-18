package com.example.loginsesame

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.data.VaultEntry
import com.example.loginsesame.data.VaultEntryDao
import com.example.loginsesame.helper.LogAssert
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws


@RunWith(AndroidJUnit4::class)
class TestShowPasswordList {

    // View is tested in TestAccountView.kt
    private lateinit var vaultEntryDao: VaultEntryDao
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun createDb() {
        Intents.init()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = UserDatabase.initDb(context)
        userDao = db.getUserDao()

        vaultEntryDao = db.getVaultEntryDao()
        val entity1 = VaultEntry(1, "account_a", "user_a", "password")
        vaultEntryDao.add(entity1)
        val entity2 = VaultEntry(2, "account_b", "user_b", "password")
        vaultEntryDao.add(entity2)
        val entity3 = VaultEntry(3, "account_c", "user_c", "password")
        vaultEntryDao.add(entity3)
        val entity4 = VaultEntry(4, "account_d", "user_d", "password")
        vaultEntryDao.add(entity4)
        val entity5 = VaultEntry(5, "account_e", "user_e", "password")
        vaultEntryDao.add(entity5)

    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        vaultEntryDao.deleteAllEntries()
        userDao.deleteAllUsers()
    }

    @Test
    @Throws(Exception::class)
    fun checkDatabaseAndView() {
        val logAssert = LogAssert()
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.typeText("randomUsername"))
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText("randomPassword"))
        // for mobile phones like Galaxy Nexus (small screen)
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.typeText("randomE-Mail"))


        //closing keyboard to press ok Button
        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withId(R.id.okButton)).perform(ViewActions.click())

        val assertArr1 = arrayOf("randomUsername")
        val assertArr2 = arrayOf("randomPassword")
        val assertArr3 = arrayOf("randomE-Mail")
        logAssert.assertLogsExist(assertArr1)
        logAssert.assertLogsExist(assertArr2)
        logAssert.assertLogsExist(assertArr3)


        assert(vaultEntryDao.allEntries().size == 5)
        assert(vaultEntryDao.getEntity("account_a") == vaultEntryDao.allEntries().get(0))
        assert(vaultEntryDao.getEntity("account_b") == vaultEntryDao.allEntries().get(1))
        assert(vaultEntryDao.getEntity("account_c") == vaultEntryDao.allEntries().get(2))
        assert(vaultEntryDao.getEntity("account_d") == vaultEntryDao.allEntries().get(3))
        assert(vaultEntryDao.getEntity("account_e") == vaultEntryDao.allEntries().get(4))

        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.rvAccounts)

        recyclerView.adapter?.itemCount

        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.rvAccounts))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


        Log.d("COUNT: ", recyclerView.adapter?.itemCount.toString())

        assert(recyclerView.adapter?.itemCount == 5)
    }

}
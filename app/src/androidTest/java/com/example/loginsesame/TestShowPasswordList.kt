package com.example.loginsesame

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.Throws
import androidx.test.rule.ActivityTestRule
import com.example.loginsesame.data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.*
import java.time.Instant

//---------------------------Run CreateTestDB before!!! ----------------------------------
@RunWith(AndroidJUnit4::class)
class TestShowPasswordList {
    // View is tested in TestAccountView.kt
    private lateinit var vaultEntryDao: VaultEntryDao
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    @get:Rule
    var activityRule = ActivityTestRule(AccountList::class.java)

    @Before
    fun createDb() {
        Intents.init()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = UserDatabase.initDb(context)
        userDao = db.getUserDao()
        vaultEntryDao = db.getVaultEntryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        //vaultEntryDao.deleteAllEntrys()
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun checkDatabaseAndView() {
        assert(vaultEntryDao.allEntrys().size == 5)
        assert(vaultEntryDao.getEntity("account_a") == vaultEntryDao.allEntrys().get(0))
        assert(vaultEntryDao.getEntity("account_b") == vaultEntryDao.allEntrys().get(1))
        assert(vaultEntryDao.getEntity("account_c") == vaultEntryDao.allEntrys().get(2))
        assert(vaultEntryDao.getEntity("account_d") == vaultEntryDao.allEntrys().get(3))
        assert(vaultEntryDao.getEntity("account_e") == vaultEntryDao.allEntrys().get(4))

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
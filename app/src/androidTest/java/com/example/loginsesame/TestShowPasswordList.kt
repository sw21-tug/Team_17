package com.example.loginsesame

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.rule.ActivityTestRule
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.data.VaultEntryDao
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.io.IOException


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
        Espresso.onView(ViewMatchers.withId(R.id.etInputPassword)).perform(ViewActions.typeText("123456789"))

        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.btnInputPasswordOK)).perform(ViewActions.click())
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
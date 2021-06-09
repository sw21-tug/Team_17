package com.example.loginsesame

import android.app.Activity
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.loginsesame.RecyclerViewAdapter.RecyclerAdapter
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.data.VaultEntry
import com.example.loginsesame.data.VaultEntryDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep
import java.util.regex.Pattern.matches

class TestSorting {
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
    fun testSortingFromAtoZ() {
        //create new account
        Espresso.onView(ViewMatchers.withId(R.id.etUsername)).perform(ViewActions.typeText("randomUsername"))
        Espresso.onView(ViewMatchers.withId(R.id.etPassword)).perform(ViewActions.typeText("randomPassword"))
        Espresso.onView(ViewMatchers.withId(R.id.etEmail)).perform(ViewActions.typeText("randomE-Mail"))

        //closing keyboard to press ok Button
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.btnOk)).perform(ViewActions.click())

        //open menu
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)

        //select sort by name from A to Z
        Espresso.onView(ViewMatchers.withText(R.string.sortfromAtoZ)).perform(ViewActions.click())

        sleep(5000)

        //onView(withId(R.id.rvAccounts)).check(ViewAssertions.matches(atPosition(1, ViewMatchers.withText("bernhard"))))


    }

    @Test
    fun testSortingFromZtoA() {
        //create new account
        Espresso.onView(ViewMatchers.withId(R.id.etUsername)).perform(ViewActions.typeText("randomUsername"))
        Espresso.onView(ViewMatchers.withId(R.id.etPassword)).perform(ViewActions.typeText("randomPassword"))
        Espresso.onView(ViewMatchers.withId(R.id.etEmail)).perform(ViewActions.typeText("randomE-Mail"))

        //closing keyboard to press ok Button
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.btnOk)).perform(ViewActions.click())

        //open menu
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)

        //select sort by name from Z to A
        Espresso.onView(ViewMatchers.withText(R.string.sortfromZtoA)).perform(ViewActions.click())

        sleep(5000)
    }
}
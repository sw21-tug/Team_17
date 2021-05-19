package com.example.loginsesame.database

import android.app.Activity
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.example.loginsesame.MainActivity
import com.example.loginsesame.R
import com.example.loginsesame.data.*
import com.example.loginsesame.helper.LogAssert
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class TestDatabaseUsers {

    // View is tested in TestAccountView.kt
    private lateinit var db: UserDatabase
    private var currentActivity: Activity? = null
    private lateinit var userRepository: UserRepository

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        Intents.init()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = UserDatabase.initDb(context)
        userRepository = UserRepository(db.getUserDao(), db.getVaultEntryDao())
        userRepository.deleteAllUsers()

        GlobalScope.launch {
            val entity1 = VaultEntry(1, "account_a", "user_a", "password")
            userRepository.insertVaultEntry(entity1)
            val entity2 = VaultEntry(2, "account_b", "user_b", "password")
            userRepository.insertVaultEntry(entity2)
            val entity3 = VaultEntry(3, "account_c", "user_c", "password")
            userRepository.insertVaultEntry(entity3)
            val entity4 = VaultEntry(4, "account_d", "user_d", "password")
            userRepository.insertVaultEntry(entity4)
            val entity5 = VaultEntry(5, "account_e", "user_e", "password")
            userRepository.insertVaultEntry(entity5)
        }
    }


    @After
    @Throws(IOException::class)
    fun cleanup() {
        userRepository.deleteAllEntries()
    }

    @Test
    @Throws(Exception::class)
    fun testAddNewUsersAndInsertVaultEntries() {
        val logAssert = LogAssert()
        Espresso.onView(ViewMatchers.withId(R.id.etUsername)).perform(ViewActions.typeText("randomUsername"))
        Espresso.onView(ViewMatchers.withId(R.id.etPassword)).perform(ViewActions.typeText("randomPassword"))
        // for mobile phones like Galaxy Nexus (small screen)
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.etEmail)).perform(ViewActions.typeText("randomE-Mail"))


        //closing keyboard to press ok Button
        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withId(R.id.btnOk)).perform(ViewActions.click())

        val assertArr1 = arrayOf("randomUsername")
        val assertArr2 = arrayOf("randomPassword")
        val assertArr3 = arrayOf("randomE-Mail")
        logAssert.assertLogsExist(assertArr1)
        logAssert.assertLogsExist(assertArr2)
        logAssert.assertLogsExist(assertArr3)

        val entries = userRepository.entries.asLiveData().blockingObserve()
        assert(entries!!.size == 5)
        assert(userRepository.getVaultEntry("account_a").asLiveData().blockingObserve() == entries[0])
        assert(userRepository.getVaultEntry("account_b").asLiveData().blockingObserve() == entries[1])
        assert(userRepository.getVaultEntry("account_c").asLiveData().blockingObserve() == entries[2])
        assert(userRepository.getVaultEntry("account_d").asLiveData().blockingObserve() == entries[3])
        assert(userRepository.getVaultEntry("account_e").asLiveData().blockingObserve() == entries[4])

        val currentActivity = getActivityInstance()
        val recyclerView = currentActivity?.findViewById<RecyclerView>(R.id.rvAccounts)

        recyclerView?.adapter?.itemCount

        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.rvAccounts))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        currentActivity?.window?.decorView
                    )
                )
            )
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        assert(recyclerView?.adapter?.itemCount == 5)
    }

    private fun getActivityInstance(): Activity? {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            val resumedActivities: Collection<*> =
                ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            if (resumedActivities.iterator().hasNext()) {
                currentActivity = resumedActivities.iterator().next() as Activity?
            }
        }
        return currentActivity
    }

    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)

        val observer = Observer<T> { t ->
            value = t
            latch.countDown()
        }

        observeForever(observer)

        latch.await(2, TimeUnit.SECONDS)
        return value
    }

}
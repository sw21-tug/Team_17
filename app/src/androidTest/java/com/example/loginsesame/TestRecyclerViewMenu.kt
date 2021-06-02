package com.example.loginsesame

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.example.loginsesame.data.*
import com.example.loginsesame.helper.LogAssert
import com.example.loginsesame.recyclerViewAdapter.RecyclerAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class TestRecyclerViewMenu {
    private lateinit var repository: UserRepository
    private lateinit var vaultEntryDao: VaultEntryDao
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase
    private var currentActivity: Activity? = null
    private val entity2 = VaultEntry(2, "account_b", "user_b", "url", "password")
    private val entity3 = VaultEntry(3, "account_c", "user_c", "url", "password")

    @get:Rule
    var rule = ActivityScenarioRule(MainActivity::class.java)

    @Rule
    @JvmField
    val executorRule = InstantTaskExecutorRule()

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
            val entity1 = VaultEntry(1, "account_a", "user_a", "url", "password")
            vaultEntryDao.add(entity1)
            // init before
            vaultEntryDao.add(entity2)
            vaultEntryDao.add(entity3)
            val entity4 = VaultEntry(4, "account_d", "user_d", "url", "password")
            vaultEntryDao.add(entity4)
            val entity5 = VaultEntry(5, "account_e", "user_e", "url", "password")
            vaultEntryDao.add(entity5)
        }
        repository = UserRepository(db.getUserDao(), db.getVaultEntryDao())
    }

    @After
    fun cleanup() {
        userDao.deleteAllUsers()
        vaultEntryDao.deleteAllEntries()
        Intents.release()
    }

    @Test
    fun testOpenOptionsMenu() {
        login()
        val logAssert = LogAssert()

        val currentActivity = getActivityInstance()
        val recyclerView = currentActivity?.findViewById<RecyclerView>(R.id.rvAccounts)
        val storeItemCount = recyclerView!!.adapter!!.itemCount
        assert(recyclerView?.adapter?.itemCount != 0)

        Espresso.onView(ViewMatchers.withId(R.id.rvAccounts)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerAdapter.AccountsViewHolder>(
                1,
                clickOnViewChild(R.id.btnDots)
            )
        )
        val assertOpened = arrayOf("Opened popup menu")
        logAssert.assertLogsExist(assertOpened)
    }

    @Test
    fun testDeleteFromOptionsMenu() {
        login()
        val logAssert = LogAssert()

        val currentActivity = getActivityInstance()
        val recyclerView = currentActivity?.findViewById<RecyclerView>(R.id.rvAccounts)
        val storeItemCount = recyclerView!!.adapter!!.itemCount

        assert(recyclerView?.adapter?.itemCount != 0)
        Espresso.onView(ViewMatchers.withId(R.id.rvAccounts)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerAdapter.AccountsViewHolder>(
                1,
                clickOnViewChild(R.id.btnDots)
            )
        )
        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withText(R.string.delete)).perform(click())

        Thread.sleep(1000)


        assert(recyclerView?.adapter?.itemCount == (storeItemCount - 1))

    }

    private fun login() {
        val logAssert = LogAssert()

        Espresso.onView(ViewMatchers.withId(R.id.etUsername))
            .perform(ViewActions.typeText("randomUsername"))
        Espresso.onView(ViewMatchers.withId(R.id.etPassword))
            .perform(ViewActions.typeText("randomPassword"))
        // for mobile phones like Galaxy Nexus (small screen)
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.etEmail))
            .perform(ViewActions.typeText("randomE-Mail"))

        //closing keyboard to press ok Button
        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withId(R.id.btnOk)).perform(click())
        val assertArr1 = arrayOf("randomUsername")
        val assertArr2 = arrayOf("randomPassword")
        val assertArr3 = arrayOf("randomE-Mail")
        logAssert.assertLogsExist(assertArr1)
        logAssert.assertLogsExist(assertArr2)
        logAssert.assertLogsExist(assertArr3)
    }

    private fun clickOnViewChild(viewId: Int) = object : ViewAction {
        override fun getConstraints() = null

        override fun getDescription() = "Click on a child view with specified id."

        override fun perform(uiController: UiController, view: View) =
            click().perform(uiController, view.findViewById<View>(viewId))
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
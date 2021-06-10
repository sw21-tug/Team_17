package com.example.loginsesame


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
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws


@RunWith(AndroidJUnit4::class)
class TestAccountView {

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
    @Throws(InterruptedException::class)
    fun testRecyclerViewVisibility() {

        val logAssert = LogAssert()
        Espresso.onView(ViewMatchers.withId(R.id.etUsername)).perform(ViewActions.typeText("randomUsername"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.etPassword)).perform(ViewActions.typeText("randomPassword"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.etEmail)).perform(ViewActions.typeText("randomE-Mail"))
        Espresso.closeSoftKeyboard()

        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withId(R.id.btnOk)).perform(ViewActions.click())

        val assertArr1 = arrayOf("randomUsername")
        val assertArr2 = arrayOf("randomPassword")
        val assertArr3 = arrayOf("randomE-Mail")
        logAssert.assertLogsExist(assertArr1)
        logAssert.assertLogsExist(assertArr2)
        logAssert.assertLogsExist(assertArr3)
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
    }

    @Test
    @Throws(InterruptedException::class)
    fun testAddAccountButton() {

        val logAssert = LogAssert()
        Espresso.onView(ViewMatchers.withId(R.id.etUsername)).perform(ViewActions.typeText("randomUsername"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.etPassword)).perform(ViewActions.typeText("randomPassword"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.etEmail)).perform(ViewActions.typeText("randomE-Mail"))
        Espresso.closeSoftKeyboard()

        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withId(R.id.btnOk)).perform(ViewActions.click())

        val assertArr1 = arrayOf("randomUsername")
        val assertArr2 = arrayOf("randomPassword")
        val assertArr3 = arrayOf("randomE-Mail")
        logAssert.assertLogsExist(assertArr1)
        logAssert.assertLogsExist(assertArr2)
        logAssert.assertLogsExist(assertArr3)

        Espresso.onView(ViewMatchers.withId(R.id.btnAddAccount)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.vaultnameEntry))
            .perform(ViewActions.typeText("randomEntryname"))
        Espresso.onView(ViewMatchers.withId(R.id.vaultURL))
            .perform(ViewActions.typeText("randomUrl"))
        Espresso.onView(ViewMatchers.withId(R.id.vaultUsername))
            .perform(ViewActions.typeText("randomUsername"))
        // for mobile phones like Galaxy Nexus (small screen)
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.vaultPassword))
            .perform(ViewActions.typeText("randomPassword"))
        // for mobile phones like Galaxy Nexus (small screen)
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.btnVaultSave)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnAddAccount)).perform(ViewActions.click())
        Thread.sleep(200)
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.btnVaultCancel)).perform(ViewActions.click())
        Thread.sleep(1000)
    }


    @Test
    @Throws(InterruptedException::class)
    fun testInsertion() {

        val logAssert = LogAssert()
        Espresso.onView(ViewMatchers.withId(R.id.etUsername)).perform(ViewActions.typeText("randomUsername"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.etPassword)).perform(ViewActions.typeText("randomPassword"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.etEmail)).perform(ViewActions.typeText("randomE-Mail"))
        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withId(R.id.btnOk)).perform(ViewActions.click())

        val assertArr1 = arrayOf("randomUsername")
        val assertArr2 = arrayOf("randomPassword")
        val assertArr3 = arrayOf("randomE-Mail")
        logAssert.assertLogsExist(assertArr1)
        logAssert.assertLogsExist(assertArr2)
        logAssert.assertLogsExist(assertArr3)

        val currentActivity = getActivityInstance()
        val recyclerView = currentActivity?.findViewById<RecyclerView>(R.id.rvAccounts)

        print(recyclerView?.adapter?.itemCount)
        assert(recyclerView?.adapter?.itemCount != 0)

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

    @Test
    @Throws(InterruptedException::class)
    fun testRemovePassword() {
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

        Espresso.onView(ViewMatchers.withId(R.id.btnOk)).perform(ViewActions.click())
        val assertArr1 = arrayOf("randomUsername")
        val assertArr2 = arrayOf("randomPassword")
        val assertArr3 = arrayOf("randomE-Mail")
        logAssert.assertLogsExist(assertArr1)
        logAssert.assertLogsExist(assertArr2)
        logAssert.assertLogsExist(assertArr3)

        val currentActivity = getActivityInstance()
        val recyclerView = currentActivity?.findViewById<RecyclerView>(R.id.rvAccounts)

        print(recyclerView?.adapter?.itemCount)
        val storeItemCount = recyclerView!!.adapter!!.itemCount
        assert(recyclerView?.adapter?.itemCount != 0)

        GlobalScope.launch { vaultEntryDao.deleteVaultEntry(entity2) }
        val updatedEntry = repository.entries.asLiveData().blockingObserve()

        Thread.sleep(5000)

        print(recyclerView?.adapter?.itemCount)

        assert(recyclerView?.adapter?.itemCount == (storeItemCount - 1))

        for (e in updatedEntry!!) {
            assert(e != entity2)
        }
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

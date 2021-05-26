package com.example.loginsesame

import android.app.Activity
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.data.UserRepository
import com.example.loginsesame.data.VaultEntry
import com.example.loginsesame.helper.LogAssert
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TestEditPassword
{

    private lateinit var db: UserDatabase
    private lateinit var repository: UserRepository
    private var currentActivity: Activity? = null

    @Rule
    @JvmField
    val rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun initDb() {
        Intents.init()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = UserDatabase.initDb(context)
        repository = UserRepository(db.getUserDao(), db.getVaultEntryDao())
        repository.deleteAllUsers()
        repository.deleteAllEntries()

        GlobalScope.launch {
            val entity1 = VaultEntry(1, "account_a", "user_a", "url", "password")
            repository.insertVaultEntry(entity1)
            val entity2 = VaultEntry(2, "account_b", "user_b", "url", "password")
            repository.insertVaultEntry(entity2)
            val entity3 = VaultEntry(3, "account_c", "user_c", "url", "password")
            repository.insertVaultEntry(entity3)
            val entity4 = VaultEntry(4, "account_d", "user_d", "url", "password")
            repository.insertVaultEntry(entity4)
            val entity5 = VaultEntry(5, "account_e", "user_e", "url", "password")
            repository.insertVaultEntry(entity5)
        }
    }

    @After
    @Throws(IOException::class)
    fun cleanup() {
        repository.deleteAllEntries()
        repository.deleteAllUsers()
    }

    @Test
    fun testOpenEditor(){
        val logAssert = LogAssert()

        onView(ViewMatchers.withId(R.id.etUsername))
            .perform(ViewActions.typeText("randomUsername"))
        onView(ViewMatchers.withId(R.id.etPassword))
            .perform(ViewActions.typeText("randomPassword"))
        // for mobile phones like Galaxy Nexus (small screen)
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.etEmail))
            .perform(ViewActions.typeText("randomE-Mail"))

        //closing keyboard to press ok Button
        closeSoftKeyboard()
        Thread.sleep(1000)

        onView(ViewMatchers.withId(R.id.btnOk)).perform(ViewActions.click())

        onView(ViewMatchers.withText("account_b")).perform(ViewActions.click())
        Thread.sleep(2000)
        val assertArr = arrayOf("Recycler Pressed Position 1")
        logAssert.assertLogsExist(assertArr)


        val currentActivity = getActivityInstance()
        val currentActivityName = currentActivity?.componentName?.className
        assert(currentActivityName.toString().equals("com.example.loginsesame.EditVaultEntry"))

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

}
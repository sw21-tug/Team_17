package com.example.loginsesame

import android.app.Activity
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.example.loginsesame.data.User
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
            val user = User(1, "Max Musterman", "test@mail.com", "123456789")
            repository.insertUser(user)

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

        onView(ViewMatchers.withId(R.id.etInputPassword))
            .perform(ViewActions.typeText("123456789"))

        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.btnInputPasswordOK)).perform(ViewActions.click())

        onView(ViewMatchers.withText("account_b")).perform(ViewActions.click())
        Thread.sleep(2000)
        val assertArr = arrayOf("Recycler Pressed Position 1")
        logAssert.assertLogsExist(assertArr)


        val currentActivity = getActivityInstance()
        val currentActivityName = currentActivity?.componentName?.className
        assert(currentActivityName.toString().equals("com.example.loginsesame.EditVaultEntry"))
    }

    @Test
    fun testOpenEditorViewData(){
        val logAssert = LogAssert()

        onView(ViewMatchers.withId(R.id.etInputPassword))
            .perform(ViewActions.typeText("123456789"))

        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.btnInputPasswordOK)).perform(ViewActions.click())

        onView(ViewMatchers.withText("account_b")).perform(ViewActions.click())
        Thread.sleep(2000)
        val assertArr = arrayOf("Recycler Pressed Position 1")
        logAssert.assertLogsExist(assertArr)

        //check if data is correct
        onView(ViewMatchers.withId(R.id.vaultURL))
            .check(ViewAssertions.matches(ViewMatchers.withText("url")))
        onView(ViewMatchers.withId(R.id.vaultUsername))
            .check(ViewAssertions.matches(ViewMatchers.withText("user_b")))
        onView(ViewMatchers.withId(R.id.vaultnameEntry))
            .check(ViewAssertions.matches(ViewMatchers.withText("account_b")))
        onView(ViewMatchers.withId(R.id.vaultPassword))
            .check(ViewAssertions.matches(ViewMatchers.withText("password")))

    }

    @Test
    fun testSaveChanges(){
        onView(ViewMatchers.withId(R.id.etInputPassword))
            .perform(ViewActions.typeText("123456789"))

        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.btnInputPasswordOK)).perform(ViewActions.click())

        onView(ViewMatchers.withText("account_b")).perform(ViewActions.click())
        Thread.sleep(2000)

        //check if data is correct
        onView(ViewMatchers.withId(R.id.vaultURL))
            .check(ViewAssertions.matches(ViewMatchers.withText("url")))
        onView(ViewMatchers.withId(R.id.vaultUsername))
            .check(ViewAssertions.matches(ViewMatchers.withText("user_b")))
        onView(ViewMatchers.withId(R.id.vaultnameEntry))
            .check(ViewAssertions.matches(ViewMatchers.withText("account_b")))
        onView(ViewMatchers.withId(R.id.vaultPassword))
            .check(ViewAssertions.matches(ViewMatchers.withText("password")))

        //overwrite data
        onView(ViewMatchers.withId(R.id.vaultURL))
            .perform(ViewActions.clearText()).perform(ViewActions.typeText("new URL"))
        onView(ViewMatchers.withId(R.id.vaultUsername))
            .perform(ViewActions.clearText()).perform(ViewActions.typeText("new user name"))
        onView(ViewMatchers.withId(R.id.vaultnameEntry))
            .perform(ViewActions.clearText()).perform(ViewActions.typeText("new entry"))
        onView(ViewMatchers.withId(R.id.vaultPassword))
            .perform(ViewActions.clearText()).perform(ViewActions.typeText("new password"))

        //save
        onView(ViewMatchers.withId(R.id.btnVaultSave)).perform(ViewActions.click())

        //go back to Main
        onView(ViewMatchers.withText("new entry")).perform(ViewActions.click())

        //check changed data
        onView(ViewMatchers.withId(R.id.vaultURL))
            .check(ViewAssertions.matches(ViewMatchers.withText("new URL")))
        onView(ViewMatchers.withId(R.id.vaultUsername))
            .check(ViewAssertions.matches(ViewMatchers.withText("new user name")))
        onView(ViewMatchers.withId(R.id.vaultnameEntry))
            .check(ViewAssertions.matches(ViewMatchers.withText("new entry")))
        onView(ViewMatchers.withId(R.id.vaultPassword))
            .check(ViewAssertions.matches(ViewMatchers.withText("new password")))
    }

    @Test
    fun testSaveChangesNoPassword(){
        onView(ViewMatchers.withId(R.id.etInputPassword))
            .perform(ViewActions.typeText("123456789"))

        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.btnInputPasswordOK)).perform(ViewActions.click())

        onView(ViewMatchers.withText("account_b")).perform(ViewActions.click())
        Thread.sleep(2000)

        //overwrite data
        onView(ViewMatchers.withId(R.id.vaultPassword))
            .perform(ViewActions.clearText())

        //save
        onView(ViewMatchers.withId(R.id.btnVaultSave)).perform(ViewActions.click())

        //check changed data
        onView(ViewMatchers.withText(R.string.vault_save_changes_alert)).check(
            ViewAssertions.matches(ViewMatchers.withText("Please enter a Password to Save the Changes")))
    }

    @Test
    fun testCancelNoChanges(){
        onView(ViewMatchers.withId(R.id.etInputPassword))
            .perform(ViewActions.typeText("123456789"))

        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.btnInputPasswordOK)).perform(ViewActions.click())

        onView(ViewMatchers.withText("account_b")).perform(ViewActions.click())
        Thread.sleep(2000)

        //cancel
        onView(ViewMatchers.withId(R.id.btnVaultCancel)).perform(ViewActions.click())

        //check
        val currentActivity = getActivityInstance()
        val currentActivityName = currentActivity?.componentName?.className
        assert(currentActivityName.toString().equals("com.example.loginsesame.MainActivity"))
    }

    @Test
    fun testCancelWithChangesNo(){
        onView(ViewMatchers.withId(R.id.etInputPassword))
            .perform(ViewActions.typeText("123456789"))

        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.btnInputPasswordOK)).perform(ViewActions.click())

        onView(ViewMatchers.withText("account_b")).perform(ViewActions.click())
        Thread.sleep(2000)

        //change data
        onView(ViewMatchers.withId(R.id.vaultURL))
            .perform(ViewActions.clearText()).perform(ViewActions.typeText("new URL"))

        //cancel
        onView(ViewMatchers.withId(R.id.btnVaultCancel)).perform(ViewActions.click())

        //check
        onView(ViewMatchers.withText(R.string.vault_cancel_changes_alert)).check(
            ViewAssertions.matches(ViewMatchers.withText("Are you sure you want to discard the changes?")))

        onView(ViewMatchers.withText(R.string.response_negative)).perform(ViewActions.click())

        val currentActivity = getActivityInstance()
        val currentActivityName = currentActivity?.componentName?.className
        assert(currentActivityName.toString().equals("com.example.loginsesame.EditVaultEntry"))
    }

    @Test
    fun testCancelWithChangesYes(){
        //return without changes
    }

    //Todo:
        //show edit entry on top - DONE
        //russian support (warnings) - DONE
        //make passwordoverview nice
        //write tests

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
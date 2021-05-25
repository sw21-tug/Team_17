package com.example.loginsesame

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.data.UserRepository
import com.example.loginsesame.helper.LogAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TestCreateVaultEntry
{

    private lateinit var db: UserDatabase
    private lateinit var repository: UserRepository

    @Rule
    @JvmField
    val rule: ActivityScenarioRule<CreateVaultEntry> = ActivityScenarioRule(CreateVaultEntry::class.java)

    @Before
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java).allowMainThreadQueries()
                .build()
        repository = UserRepository(db.getUserDao(), db.getVaultEntryDao())
    }

    @After
    @Throws(IOException::class)
    fun cleanup() {
        repository.deleteAllEntries()
    }

    @Test
    fun clickSaveWithFullData(){
        val logAssert = LogAssert()
        onView(ViewMatchers.withId(R.id.vaultnameEntry)).perform(ViewActions.typeText("random"))
        onView(ViewMatchers.withId(R.id.vaultURL)).perform(ViewActions.typeText("http://www.example.com/"))
        onView(ViewMatchers.withId(R.id.vaultUsername)).perform(ViewActions.typeText("randomUser"))
        onView(ViewMatchers.withId(R.id.vaultPassword)).perform(ViewActions.typeText("randomPassword"))

        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.btnVaultSave)).perform(ViewActions.click())

        //val assertButton = arrayOf("createButtonClicked")
        //logAssert.assertLogsExist(assertButton)

        val assertEntryName = arrayOf("randomEntryName")
        logAssert.assertLogsExist(assertEntryName)
        val assertUrl = arrayOf("randomUrl")
        logAssert.assertLogsExist(assertUrl)
        val assertUserName = arrayOf("randomUsername")
        logAssert.assertLogsExist(assertUserName)
        val assertPassword = arrayOf("randomPassword")
        logAssert.assertLogsExist(assertPassword)

    }
    @Test
    fun clickSaveWithName(){
        val logAssert = LogAssert()
        onView(ViewMatchers.withId(R.id.vaultnameEntry)).perform(ViewActions.typeText("random"))
        onView(ViewMatchers.withId(R.id.vaultUsername)).perform(ViewActions.typeText("randomUser"))
        onView(ViewMatchers.withId(R.id.vaultPassword)).perform(ViewActions.typeText("randomPassword"))

        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.btnVaultSave)).perform(ViewActions.click())

        //val assertButton = arrayOf("createButtonClicked")
        //logAssert.assertLogsExist(assertButton)

        val assertEntryName = arrayOf("randomEntryName")
        logAssert.assertLogsExist(assertEntryName)
        val assertUrl = arrayOf("randomUrl")
        logAssert.assertLogsExist(assertUrl)
        val assertUserName = arrayOf("randomUsername")
        logAssert.assertLogsExist(assertUserName)
        val assertPassword = arrayOf("randomPassword")
        logAssert.assertLogsExist(assertPassword)
    }

    @Test
    fun clickSaveWithUrl(){
        val logAssert = LogAssert()
        onView(ViewMatchers.withId(R.id.vaultURL)).perform(ViewActions.typeText("http://www.example.com/"))
        onView(ViewMatchers.withId(R.id.vaultUsername)).perform(ViewActions.typeText("randomUser"))
        onView(ViewMatchers.withId(R.id.vaultPassword)).perform(ViewActions.typeText("randomPassword"))

        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.btnVaultSave)).perform(ViewActions.click())

        //val assertButton = arrayOf("createButtonClicked")
        //logAssert.assertLogsExist(assertButton)

        val assertEntryName = arrayOf("randomEntryName")
        logAssert.assertLogsExist(assertEntryName)
        val assertUrl = arrayOf("randomUrl")
        logAssert.assertLogsExist(assertUrl)
        val assertUserName = arrayOf("randomUsername")
        logAssert.assertLogsExist(assertUserName)
        val assertPassword = arrayOf("randomPassword")
        logAssert.assertLogsExist(assertPassword)
    }

    @Test
    fun clickSaveWithIncompleteData(){
        //incorrectData
        val logAssert = LogAssert()
        onView(ViewMatchers.withId(R.id.vaultUsername)).perform(ViewActions.typeText("randomUser"))
        onView(ViewMatchers.withId(R.id.vaultPassword)).perform(ViewActions.typeText("randomPassword"))

        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.btnVaultSave)).perform(ViewActions.click())

        //val assertButton = arrayOf("createButtonClicked")
        //logAssert.assertLogsExist(assertButton)

        val assertMsg = arrayOf("incorrectData")
        logAssert.assertLogsExist(assertMsg)
    }

    @Test
    fun clickCancel(){
        val logAssert = LogAssert()
        onView(ViewMatchers.withId(R.id.btnVaultCancel)).perform(ViewActions.click())

        val assertButton = arrayOf("cancelButton")
        logAssert.assertLogsExist(assertButton)
    }

}
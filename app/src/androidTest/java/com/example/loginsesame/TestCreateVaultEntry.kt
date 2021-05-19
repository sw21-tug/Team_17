package com.example.loginsesame

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.loginsesame.helper.LogAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestCreateVaultEntry
{

    @Rule
    @JvmField
    val rule: ActivityTestRule<CreateVaultEntry> = ActivityTestRule(CreateVaultEntry::class.java)

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
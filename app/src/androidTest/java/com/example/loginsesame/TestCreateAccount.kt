package com.example.loginsesame

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.loginsesame.helper.LogAssert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class TestCreateAccount {

    @Rule
    @JvmField
    val rule: ActivityTestRule<CreateStartUp> = ActivityTestRule(CreateStartUp::class.java)

    @Test
    fun okButtonClickable() {

        val logAssert = LogAssert()
        onView(withId(R.id.username)).perform(ViewActions.typeText("randomUsername"))
        onView(withId(R.id.password)).perform(ViewActions.typeText("randomPassword"))
        // for mobile phones like Galaxy Nexus (small screen)
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.email)).perform(ViewActions.typeText("randomE-Mail"))


        //closing keyboard to press ok Button
        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)

        onView(withId(R.id.okButton)).perform(ViewActions.click())

        val assertArr1okbuttonTest = arrayOf("randomUsername")
        val assertArr2okbuttonTest = arrayOf("randomPassword")
        val assertArr3okbuttonTest = arrayOf("randomE-Mail")
        logAssert.assertLogsExist(assertArr1okbuttonTest)
        logAssert.assertLogsExist(assertArr2okbuttonTest)
        logAssert.assertLogsExist(assertArr3okbuttonTest)

    }

    @Test
    fun cancelButtonClickable() {

        val logAssert = LogAssert()
        onView(withId(R.id.cancelButton)).perform(ViewActions.click())

        val assertArr = arrayOf("cancelButton")
        logAssert.assertLogsExist(assertArr)

    }

}
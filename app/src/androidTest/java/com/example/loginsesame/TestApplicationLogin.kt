package com.example.loginsesame

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
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestApplicationLogin {

    @Rule
    @JvmField
    val rule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)


    @Test
    fun userEntersPasswordBtnOk() {

        val logAssert = LogAssert()
        onView(withId(R.id.etInputPassword)).perform(ViewActions.typeText("Daniel"))
        onView(withId(R.id.btnInputPasswordOK)).perform(ViewActions.click())

        val assertArr = arrayOf("btnInputPasswordOK")
        logAssert.assertLogsExist(assertArr)

    }

    @Test
    fun userEntersPasswordBtnCancel() {

        val logAssert = LogAssert()
        onView(withId(R.id.etInputPassword)).perform(ViewActions.typeText("Daniel"))
        onView(withId(R.id.btnInputPasswordCancel)).perform(ViewActions.click())

        val assertArr = arrayOf("btnInputPasswordCancel")
        logAssert.assertLogsExist(assertArr)

    }

    @Test
    fun userPressesBackButton() {

        val logAssert = LogAssert()

        Espresso.pressBack()

        val assertArr = arrayOf("Back-Button Pressed With No Action")
        logAssert.assertLogsExist(assertArr)

    }

}
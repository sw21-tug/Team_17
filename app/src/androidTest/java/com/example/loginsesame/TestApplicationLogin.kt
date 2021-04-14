package com.example.loginsesame

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule

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
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)


    @Test
    fun userEntersPasswordBtnOk() {

        onView(withId(R.id.etInputPassword)).perform(typeText("Daniel"))
        onView(withId(R.id.btnInputPasswordOK)).perform(click())

    }

    @Test
    fun userEntersPasswordBtnCancel() {
        // Context of the app under test.

        onView(withId(R.id.etInputPassword)).perform(typeText("Daniel"))
        onView(withId(R.id.btnInputPasswordCancel)).perform(click())


    }


    @Test
    fun userEntersEmptyPasswordBtnOk() {

        //onView(withId(R.id.etInputPassword)).perform(typeText("Daniel"))
        onView(withId(R.id.btnInputPasswordOK)).perform(click())



    }

}
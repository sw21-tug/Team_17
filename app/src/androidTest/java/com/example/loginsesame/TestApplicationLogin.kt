package com.example.loginsesame

import android.content.Context
import android.service.autofill.Validators.not
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.loginsesame.data.User
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.helper.LogAssert
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

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

    @Before
    fun initIntend() {
        Intents.init()
    }

    @After
    fun cleanUp() {
        Intents.release()
    }

    @Test
    fun userEntersCorrectPasswordClicksOk() {

        onView(withId(R.id.etInputPassword)).perform(ViewActions.typeText("123456789"))
        onView(withId(R.id.btnInputPasswordOK)).perform(ViewActions.click())

        intended(hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun userEntersIncorrectPasswordClicksOk() {


        onView(withId(R.id.etInputPassword)).perform(ViewActions.typeText("randomPassword1"))
        onView(withId(R.id.btnInputPasswordOK)).perform(ViewActions.click())

        onView(withText("Incorrect Password")).check(matches(isDisplayed()))
    }


    @Test
    fun userEntersIncorrectPasswordClicksCancel() {

        val logAssert = LogAssert()
        onView(withId(R.id.etInputPassword)).perform(ViewActions.typeText("randomPassword1"))
        onView(withId(R.id.btnInputPasswordCancel)).perform(ViewActions.click())

        val assertArr = arrayOf("btnInputPasswordCancel")
        logAssert.assertLogsExist(assertArr)

    }

    @Test
    fun userClicksBackButton() {

        val logAssert = LogAssert()

        Espresso.pressBack()

        val assertArr = arrayOf("Back-Button Pressed With No Action")
        logAssert.assertLogsExist(assertArr)

    }

}
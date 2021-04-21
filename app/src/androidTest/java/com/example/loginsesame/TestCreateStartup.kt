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

@RunWith(AndroidJUnit4::class)
class TestCreateStartup {

    @Rule
    @JvmField
    val rule: ActivityTestRule<CreateStartUp> = ActivityTestRule(CreateStartUp::class.java)

    @Test
    fun okButtonClickable() {

        val logAssert = LogAssert()
        onView(withId(R.id.okButton)).perform(ViewActions.typeText("randomPassword1"))
        onView(withId(R.id.btnInputPasswordOK)).perform(ViewActions.click())

        val assertArr = arrayOf("btnInputPasswordOK")
        logAssert.assertLogsExist(assertArr)

    }

    @Test
    fun userEntersPasswordBtnCancel() {

        val logAssert = LogAssert()
        onView(withId(R.id.etInputPassword)).perform(ViewActions.typeText("randomPassword1"))
        onView(withId(R.id.btnInputPasswordCancel)).perform(ViewActions.click())

        val assertArr = arrayOf("btnInputPasswordCancel")
        logAssert.assertLogsExist(assertArr)

    }
}
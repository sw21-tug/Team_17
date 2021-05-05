package com.example.loginsesame

import android.content.Context
import android.content.Intent
import android.service.autofill.UserData
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
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.loginsesame.data.User
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.helper.LogAssert
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.concurrent.thread

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestLogin {
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    @Rule
    @JvmField
    val rule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    @Before
    fun db_init() {
        Intents.init()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = UserDatabase.initDb(context)
        userDao = db.getUserDao()

        val user = User(null, "Max Musterman", "test@mail.com", "123456789")
        userDao.deleteAllUsers()
        userDao.insertUser(user)
    }

    @After
    fun cleanup() {
        userDao.deleteAllUsers()
        Intents.release()
    }

    @Test
    fun userEntersCorrectPasswordClicksOk() {
        onView(withId(R.id.etInputPassword)).perform(ViewActions.typeText("123456789"))

        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btnInputPasswordOK)).perform(ViewActions.click())
        //rule.launchActivity(Intent())

        //intended(hasComponent(MainActivity::class.java.name))
        intended(hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun userEntersIncorrectPasswordClicksOk() {

        val logAssert = LogAssert()
        onView(withId(R.id.etInputPassword)).perform(ViewActions.typeText("randomPassword1"))

        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btnInputPasswordOK)).perform(ViewActions.click())
        //rule.launchActivity(Intent())

        val assertArr = arrayOf("Incorrect Password")
        logAssert.assertLogsExist(assertArr)
    }


    @Test
    fun userEntersIncorrectPasswordClicksCancel() {
        val logAssert = LogAssert()
        onView(withId(R.id.etInputPassword)).perform(ViewActions.typeText("randomPassword1"))

        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btnInputPasswordCancel)).perform(ViewActions.click())

        val assertArr = arrayOf("btnInputPasswordCancel")
        logAssert.assertLogsExist(assertArr)
    }

    @Test
    fun userClicksBackButton() {
        val logAssert = LogAssert()

        //Second pressBack is needed if software keyboard is open, so keyboard needs to be closed before
        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)
        Espresso.pressBack()

        val assertArr = arrayOf("Back-Button Pressed With No Action")
        logAssert.assertLogsExist(assertArr)

    }

}
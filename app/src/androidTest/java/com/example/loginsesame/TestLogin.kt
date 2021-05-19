package com.example.loginsesame

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.loginsesame.data.User
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.helper.LogAssert
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
    val rule: ActivityScenarioRule<LoginActivity> = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun initDbAndIntents() {
        Intents.init()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = UserDatabase.initDb(context)
        userDao = db.getUserDao()

        val user = User(null, "Max Musterman", "test@mail.com", "123456789")
        userDao.deleteAllUsers()
        GlobalScope.launch {
            userDao.insertUser(user)
        }

    }

    @After
    fun cleanup() {
        userDao.deleteAllUsers()
        Intents.release()
    }

    @Test
    fun testCorrectPasswordClickOk() {
        onView(withId(R.id.etInputPassword)).perform(ViewActions.typeText("123456789"))

        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btnInputPasswordOK)).perform(ViewActions.click())
        //rule.launchActivity(Intent())

        //intended(hasComponent(MainActivity::class.java.name))
        intended(hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun testIncorrectPasswordClickOk() {

        val logAssert = LogAssert()
        onView(withId(R.id.etInputPassword)).perform(ViewActions.typeText("randomPassword1"))

        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)
        onView(withId(R.id.btnInputPasswordOK)).perform(ViewActions.click())
        //rule.launchActivity(Intent())

        val assertArr = arrayOf("Incorrect Password")
        logAssert.assertLogsExist(assertArr)
    }


    @Test
    fun testIncorrectPasswordClickCancel() {
        val logAssert = LogAssert()
        onView(withId(R.id.etInputPassword)).perform(ViewActions.typeText("randomPassword1"))

        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)
        onView(withId(R.id.btnInputPasswordCancel)).perform(ViewActions.click())

        val assertArr = arrayOf("btnInputPasswordCancel")
        logAssert.assertLogsExist(assertArr)
    }

    @Test
    fun testBackButtonWithoutAction() {
        val logAssert = LogAssert()

        //Second pressBack is needed if software keyboard is open, so keyboard needs to be closed before
        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)
        Espresso.pressBack()

        val assertArr = arrayOf("Back-Button Pressed With No Action")
        logAssert.assertLogsExist(assertArr)

    }

}
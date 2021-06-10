package com.example.loginsesame

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.helper.LogAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestCreateAccount {

    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    @Rule
    @JvmField
    val rule: ActivityScenarioRule<CreateNewUserActivity> = ActivityScenarioRule(CreateNewUserActivity::class.java)

    @Before
    fun initDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = UserDatabase.initDb(context)
        userDao = db.getUserDao()
        userDao.deleteAllUsers()
        //Intents.release()
    }

    @After
    fun cleanup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = UserDatabase.initDb(context)
        userDao = db.getUserDao()
        userDao.deleteAllUsers()
        //Intents.release()
    }

    @Test
    fun testIfOkButtonIsClickable() {

        val logAssert = LogAssert()
        onView(withId(R.id.etUsername)).perform(typeText("randomUsername"))
        closeSoftKeyboard()
        onView(withId(R.id.etPassword)).perform(typeText("randomPassword"))
        closeSoftKeyboard()
        onView(withId(R.id.etEmail)).perform(typeText("randomE-Mail"))
        closeSoftKeyboard()



        Thread.sleep(1000)

        onView(withId(R.id.btnOk)).perform(click())

        val assertArr1 = arrayOf("randomUsername")
        val assertArr2 = arrayOf("randomPassword")
        val assertArr3 = arrayOf("randomE-Mail")
        logAssert.assertLogsExist(assertArr1)
        logAssert.assertLogsExist(assertArr2)
        logAssert.assertLogsExist(assertArr3)
    }

    @Test
    fun testBackButtonWithoutAction() {
        val logAssert = LogAssert()

        //Second pressBack is needed if software keyboard is open, so keyboard needs to be closed before
        closeSoftKeyboard()
        Thread.sleep(1000)
        Espresso.pressBack()

        val assertArr = arrayOf("Back-Button Pressed With No Action")
        logAssert.assertLogsExist(assertArr)

    }

}

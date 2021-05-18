package com.example.loginsesame

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.loginsesame.data.User
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestLanguageSupport {
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    @Rule
    @JvmField
    val rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun initDbAndIntents() {

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
    fun testSwitchToEnglish() {

        //create new account
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.typeText("randomUsername"))
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText("randomPassword"))
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.typeText("randomE-Mail"))

        //closing keyboard to press ok Button
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.okButton)).perform(ViewActions.click())

        //open menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        //set language
        Espresso.onView(withText(R.string.language_set_text)).perform(ViewActions.click())
        Espresso.onView(withText(R.string.language_en)).perform(ViewActions.click())
        Espresso.onView(withText(R.string.btn_ok)).perform(ViewActions.click())

        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        //assert if text is russian
        Espresso.onView(withText(R.string.language_set_text)).check(matches(withText("Set App Language")))

    }

    @Test
    fun testSwitchToRussian() {
        //create new account
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.typeText("randomUsername"))
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText("randomPassword"))
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.typeText("randomE-Mail"))

        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.okButton)).perform(ViewActions.click())

        //open menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        //set language
        Espresso.onView(withText(R.string.language_set_text)).perform(ViewActions.click())
        Espresso.onView(withText(R.string.language_ru)).perform(ViewActions.click())
        Espresso.onView(withText(R.string.btn_ok)).perform(ViewActions.click())

        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        //assert if text is russian
        Espresso.onView(withText(R.string.language_set_text)).check(matches(withText("Установите язык на русский")))

    }

}

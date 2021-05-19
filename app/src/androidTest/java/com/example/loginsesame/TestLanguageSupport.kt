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
import com.example.loginsesame.data.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestLanguageSupport {
    private lateinit var repository: UserRepository
    private lateinit var db: UserDatabase

    @Rule
    @JvmField
    val rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun initDbAndIntents() {

        Intents.init()

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = UserDatabase.initDb(context)
        repository = UserRepository(db.getUserDao(), db.getVaultEntryDao())

        val user = User(null, "Max Musterman", "test@mail.com", "123456789")
        repository.deleteAllUsers()
    }

    @After
    fun cleanup() {
        repository.deleteAllUsers()

        Intents.release()
    }

    @Test
    fun testSwitchToEnglish() {

        //create new account
        Espresso.onView(ViewMatchers.withId(R.id.etUsername)).perform(ViewActions.typeText("randomUsername"))
        Espresso.onView(ViewMatchers.withId(R.id.etPassword)).perform(ViewActions.typeText("randomPassword"))
        Espresso.onView(ViewMatchers.withId(R.id.etEmail)).perform(ViewActions.typeText("randomE-Mail"))

        //closing keyboard to press ok Button
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.btnOk)).perform(ViewActions.click())

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
        Espresso.onView(ViewMatchers.withId(R.id.etUsername)).perform(ViewActions.typeText("randomUsername"))
        Espresso.onView(ViewMatchers.withId(R.id.etPassword)).perform(ViewActions.typeText("randomPassword"))
        Espresso.onView(ViewMatchers.withId(R.id.etEmail)).perform(ViewActions.typeText("randomE-Mail"))

        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.btnOk)).perform(ViewActions.click())

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

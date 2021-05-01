package com.example.loginsesame

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.loginsesame.helper.LogAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestLanguageSupport {

    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setPrimaryLanguage() {

    }

    @After
    fun resetPrimaryLanguage() {

    }

    @Test
    fun testSwitchFromEnglishToRussian() {
        
        //setup app language is english

        //click on ...
        //click on "Switch to Russian"
        //check if Login Sesame => Сука Блядь

    }

    @Test
    fun testSwitchFromRussianToEnglish() {

        //click on ...
        //click on "Switch to English"
        //check if Сука Блядь => Login Sesame

    }
}

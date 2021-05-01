package com.example.loginsesame

import android.content.Context
import android.content.Intent
import android.service.autofill.UserData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.rule.ActivityTestRule
import com.example.loginsesame.data.User
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import org.junit.*
import java.io.IOException


class TestMainActivity {
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    @Before
    fun db_init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = UserDatabase.initDb(context)
        userDao = db.getUserDao()
    }

    @After
    @Throws(IOException::class)
    fun cleanup() {
        Intents.release()
    }

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun init() {
        Intents.init()
    }

    @Test
    @Throws(Exception::class)
    fun createActivityOpened(){
        userDao.deleteAllUsers()
        Thread.sleep(1000)

        rule.launchActivity(Intent())
        intended(hasComponent(CreateStartUp::class.java.name))
    }

    @Test
    @Throws(Exception::class)
    fun loginActivityOpened(){
        userDao.deleteAllUsers()
        val user = User(1, "Max Musterman", "test@mail.com", "123456789")
        userDao.insertUser(user)
        Thread.sleep(1000)

        rule.launchActivity(Intent())

        intended(hasComponent(LoginActivity::class.java.name))
    }


}
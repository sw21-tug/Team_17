package com.example.loginsesame.main

import android.app.Activity
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.example.loginsesame.MainActivity
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import org.junit.*
import java.io.IOException


class TestMainCreateUser {
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase
    private var currentActivity: Activity? = null

    @Rule
    @JvmField
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = UserDatabase.initDb(context)
        userDao = db.getUserDao()

        Thread.sleep(1000)
    }

    @After
    @Throws(IOException::class)
    fun cleanup() {
        userDao.deleteAllUsers()
    }

    @Test
    @Throws(Exception::class)
    fun testOpenCreateUserWhenEmptyDb(){

        val currentActivity = getActivityInstance()
        val currentActivityName = currentActivity?.componentName?.className

        assert(currentActivityName.toString().equals("com.example.loginsesame.CreateNewUserActivity"))
    }



    private fun getActivityInstance(): Activity? {
        getInstrumentation().runOnMainSync {
            val resumedActivities: Collection<*> =
                ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            if (resumedActivities.iterator().hasNext()) {
                currentActivity = resumedActivities.iterator().next() as Activity?
            }
        }
        return currentActivity
    }
}

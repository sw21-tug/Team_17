package com.example.loginsesame


import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.jvm.Throws


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestAccountView {

    @get:Rule
    var activityRule = ActivityTestRule(AccountList::class.java)

    @Test
    @Throws(InterruptedException::class)
    fun testVisibilityRecyclerView() {

        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.rvAccounts)

        recyclerView.adapter?.itemCount

        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.rvAccounts))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    @Throws(InterruptedException::class)
    fun checkInsertionTest() {


        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.rvAccounts)

        print(recyclerView.adapter?.itemCount)
        Log.d("COUNT: ", recyclerView.adapter?.itemCount.toString())


        assert(recyclerView.adapter?.itemCount != 0)

    }
}
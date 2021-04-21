package com.example.loginsesame

import org.junit.Test

import org.junit.Assert.*
import kotlin.Boolean.Companion as val

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    // Test for [LS-001-A] Create Startup (free to choose another name) activity
    @Test
    fun testOkBottom(){
        val okTest = false;
        okTest = okBottom.isAktiv(); // richtige Bottom ist gedrückt boolean?
        assertTrue(okTest);
    }

    @Test
    fun testCancelBottom(){
        val cancelTest = false;
        cancelTest = cancelBottom.isAktiv(); // richtige Bottom ist gedrückt boolean?
        assertTrue(cancelTest);
    }

   @Test
   fun testUsernameString(){
        assertTrue(userName.isString());
   }

    @Test
    fun testPasswordString(){
        assertTrue(password.isString());
    }

    @Test
    fun testEmailString(){
        assertTrue(email.isString());
    }


}
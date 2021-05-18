package com.example.loginsesame.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.loginsesame.data.User
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.Throws
import androidx.test.rule.ActivityTestRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.*

@RunWith(AndroidJUnit4::class)
class TestDatabase {
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    @Before
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = UserDatabase.initDb(context)
        userDao = db.getUserDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        userDao.deleteAllUsers()
        Intents.release()
    }

    @Test
    @Throws(Exception::class)
    fun testReadAndWriteUserInDatabase() {
        userDao.deleteAllUsers()
        val user = User(1, "Max Musterman", "test@mail.com", "123456789")
        val rowid = userDao.insertUser(user)
        Assert.assertNotNull(rowid)
        val createdUsers = userDao.getAllUsers()
        assert(createdUsers.size == 1)

        userDao.deleteUser(user)
        val deletedUsers = userDao.getAllUsers()
        assert(deletedUsers.isEmpty())
    }

}
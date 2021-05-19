package com.example.loginsesame.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.loginsesame.data.User
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.data.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class TestDatabase {
    private lateinit var userRepository: UserRepository
    private lateinit var db: UserDatabase

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java).allowMainThreadQueries().build()
        userRepository = UserRepository(db.getUserDao(), db.getVaultEntryDao())
        userRepository.deleteAllUsers()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testReadAndWriteUserInDatabase() {
        val user = User(1, "Max Musterman", "test@mail.com", "123456789")
        GlobalScope.launch {
            userRepository.insertUser(user)
        }
        val usersInsert = userRepository.users.asLiveData().blockingObserve()
        assert(usersInsert!!.size == 1)
        GlobalScope.launch {
            userRepository.deleteAllUsers()
        }
        val usersDelete = userRepository.users.asLiveData().blockingObserve()
        assert(usersDelete!!.isEmpty())
    }

    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)

        val observer = Observer<T> { t ->
            value = t
            latch.countDown()
        }

        observeForever(observer)

        latch.await(2, TimeUnit.SECONDS)
        return value
    }

}
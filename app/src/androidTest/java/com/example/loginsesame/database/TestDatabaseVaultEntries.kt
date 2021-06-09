package com.example.loginsesame.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.data.UserRepository
import com.example.loginsesame.data.VaultEntry
import com.example.loginsesame.data.VaultEntryDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class TestDatabaseVaultEntries {

    private lateinit var db: UserDatabase
    private lateinit var repository: UserRepository
    private val entry = VaultEntry(1, "account_x", "user_x", "url", "password")

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()


    @Before
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java).allowMainThreadQueries()
                .build()
        repository = UserRepository(db.getUserDao(), db.getVaultEntryDao())
    }

    @After
    @Throws(IOException::class)
    fun cleanup() {
        repository.deleteAllEntries()
        repository.deleteAllUsers()
    }


    @Test
    fun testAddEntity() {
        val entity = VaultEntry(2, "account_z", "user_z", "url", "password")
        GlobalScope.launch {
            repository.insertVaultEntry(entity)
        }
        Thread.sleep(2000)
        val entries = repository.entries.asLiveData().blockingObserve()
        assert(entries!![0] == entity)
    }

    @Test
    fun testGetEntity() {
        GlobalScope.launch {
            repository.insertVaultEntry(entry)
        }
        val entity = repository.getVaultEntry("account_x").asLiveData().blockingObserve()
        assert(entity == entry)
    }

    @Test
    fun testDeleteEntity() {
        GlobalScope.launch {
            repository.insertVaultEntry(entry)
        }
        repository.entries.asLiveData().blockingObserve()
        GlobalScope.launch {
            repository.deleteAllEntries()
            repository.deleteAllUsers()
        }
        val entries = repository.entries.asLiveData().blockingObserve()

        if (entries != null) {
            assert(entries.isEmpty())
        } else {
            assert(false)
        }
    }

    @Test
    fun testEditEntity() {
        val entityB = VaultEntry(3, "account_b", "user_b", "url", "password")
        GlobalScope.launch {
            repository.insertVaultEntry(entityB)
        }
        Thread.sleep(2000)
        val entries = repository.entries.asLiveData().blockingObserve()
        assert(entries!![0] == entityB)

        val newEntityB = VaultEntry(3, "account_y", "user_y", "url", "password_y")
        GlobalScope.launch {
            repository.updateVaultEntry(newEntityB)
        }
        val updatedEntry = repository.entries.asLiveData().blockingObserve()
        assert(updatedEntry!![0] == newEntityB)
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

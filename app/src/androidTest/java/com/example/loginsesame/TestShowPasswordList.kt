package com.example.loginsesame

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.Throws
import androidx.test.rule.ActivityTestRule
import com.example.loginsesame.data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.*
import java.time.Instant


@RunWith(AndroidJUnit4::class)
class TestShowPasswordList {
    // View is tested in TestAccountView.kt
    private lateinit var vaultEntryDao: VaultEntryDao
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    @get:Rule
    var activityRule = ActivityTestRule(AccountList::class.java)

    @Before
    fun createDb() {
        Intents.init()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, UserDatabase::class.java).build()
        userDao = db.getUserDao()
        vaultEntryDao = db.getVaultEntryDao()

        vaultEntryDao.deleteAllEntrys()

        val entity1 = VaultEntry(1, "account_a", "user_a", "password")
        vaultEntryDao.add(entity1)
        val entity2 = VaultEntry(2, "account_b", "user_b", "password")
        vaultEntryDao.add(entity2)
        val entity3 = VaultEntry(3, "account_c", "user_c", "password")
        vaultEntryDao.add(entity3)
        val entity4 = VaultEntry(4, "account_d", "user_d", "password")
        vaultEntryDao.add(entity4)
        val entity5 = VaultEntry(5, "account_e", "user_e", "password")
        vaultEntryDao.add(entity5)


    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        vaultEntryDao.deleteAllEntrys()
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun checkDatabase() {

        assert(vaultEntryDao.allEntrys().size == 5)
        assert(vaultEntryDao.getEntity("account_a") == vaultEntryDao.allEntrys().get(0))
        assert(vaultEntryDao.getEntity("account_b") == vaultEntryDao.allEntrys().get(1))
        assert(vaultEntryDao.getEntity("account_c") == vaultEntryDao.allEntrys().get(2))
        assert(vaultEntryDao.getEntity("account_d") == vaultEntryDao.allEntrys().get(3))
        assert(vaultEntryDao.getEntity("account_e") == vaultEntryDao.allEntrys().get(4))
    }

}
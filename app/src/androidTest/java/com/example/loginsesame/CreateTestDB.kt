package com.example.loginsesame

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.data.VaultEntry
import com.example.loginsesame.data.VaultEntryDao
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class CreateTestDB {
    // View is tested in TestAccountView.kt
    private lateinit var vaultEntryDao: VaultEntryDao
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    @Before
    fun createDb() {
        Intents.init()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = UserDatabase.initDb(context)
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
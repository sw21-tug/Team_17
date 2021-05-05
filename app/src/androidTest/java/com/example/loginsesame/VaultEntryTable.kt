package com.example.loginsesame

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import com.example.loginsesame.data.VaultEntry
import com.example.loginsesame.data.VaultEntryDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.jvm.Throws

class VaultEntryTable {

    private lateinit var vaultEntryDao: VaultEntryDao
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, UserDatabase::class.java).build()
        userDao = db.getUserDao()
        vaultEntryDao = db.getVaultEntryDao()
        val entity = VaultEntry(1, "account_x", "url", "user_x", "password")
        vaultEntryDao.add(entity)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    fun addEntity(){
        val entity = VaultEntry(2, "account_z","url", "user_z", "password")
        vaultEntryDao.add(entity)
        assert(vaultEntryDao.allEntrys().get(1) == entity)
    }

    @Test
    fun vaultEntry(){
        assert(vaultEntryDao.getEntity("account_x") == vaultEntryDao.allEntrys().get(0))
    }

    @Test
    fun removeEntity(){
        vaultEntryDao.deleteVaultEntry(vaultEntryDao.allEntrys().get(0))
        assert(vaultEntryDao.getEntity("account_x") == null)
    }

    @Test
    fun editEntity(){
        val entity_b = VaultEntry(3, "account_b", "url","user_b", "password")
        vaultEntryDao.add(entity_b)
        val entity = VaultEntry(3, "account_y", "url","user_y", "password_y")
        vaultEntryDao.updateVaultEntry(entity)
        assert(vaultEntryDao.getEntity("account_y") == entity)
    }
}
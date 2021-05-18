package com.example.loginsesame

import android.content.Context
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
        db = UserDatabase.initDb(context)
        userDao = db.getUserDao()
        userDao.deleteAllUsers()
        vaultEntryDao = db.getVaultEntryDao()
        vaultEntryDao.deleteAllEntries()
        val entity = VaultEntry(1, "account_x", "user_x", "password")
        vaultEntryDao.add(entity)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        userDao.deleteAllUsers()
        vaultEntryDao.deleteAllEntries()
        //db.close()
    }


    @Test
    fun addEntity(){
        val entity = VaultEntry(2, "account_z", "user_z", "password")
        vaultEntryDao.add(entity)
        assert(vaultEntryDao.allEntries().get(1) == entity)
    }

    @Test
    fun vaultEntry(){
        assert(vaultEntryDao.getEntity("account_x") == vaultEntryDao.allEntries().get(0))
    }

    @Test
    fun removeEntity(){
        vaultEntryDao.deleteVaultEntry(vaultEntryDao.allEntries().get(0))
        assert(vaultEntryDao.getEntity("account_x") == null)
    }

    @Test
    fun editEntity(){
        val entity_b = VaultEntry(3, "account_b", "user_b", "password")
        vaultEntryDao.add(entity_b)
        val entity = VaultEntry(3, "account_y", "user_y", "password_y")
        vaultEntryDao.updateVaultEntry(entity)
        assert(vaultEntryDao.getEntity("account_y") == entity)
    }
}
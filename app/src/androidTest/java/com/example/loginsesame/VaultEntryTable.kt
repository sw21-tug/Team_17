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
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    fun addEntity(){
        val entity = VaultEntry(1, "accout_x", "user_x", "password")
        vaultEntryDao.add(entity)
        assert(vaultEntryDao.allEntrys().get(0) == entity)
    }

    @Test
    fun vaultEntry(){
        assert(vaultEntryDao.getEntity("accout_x") == db.AllUsers().at(1))
    }

    @Test
    fun removeEntity(){
        db.remove("accout_x")
        assert(db.getEntity("accout_x") == null)

    }

    @Test
    fun editEntity(){
        val entity = Entity(2, "accout_y", "user_y", "password_y")
        db.edit(entity)
        assert(db.AllUsers().at(1) == entity)
    }
}
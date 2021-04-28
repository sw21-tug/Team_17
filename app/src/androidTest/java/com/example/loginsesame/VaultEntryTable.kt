package com.example.loginsesame

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.loginsesame.data.UserDao
import com.example.loginsesame.data.UserDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.jvm.Throws

class VaultEntryTable {
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, UserDatabase::class.java).build()
        userDao = db.getUserDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    fun addEntity(){
        val entity = Entity(2, "accout_x", "user_x", "password")
        db.add(entity)
        assert(db.AllUsers().at(1) == entity)
    }

    @Test
    fun vaultEntry(){
        assert(db.getEntity("accout_x") == db.AllUsers().at(1))
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
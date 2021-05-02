package com.example.loginsesame.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [User::class, VaultEntry::class])
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getVaultEntryDao(): VaultEntryDao

    companion object{
        @Volatile private var INSTANCE: UserDatabase? = null

        fun initDb(context: Context): UserDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val db = Room.databaseBuilder(
                    context,
                    UserDatabase::class.java,
                    "user_database.db"
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}
package com.example.loginsesame.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)
}
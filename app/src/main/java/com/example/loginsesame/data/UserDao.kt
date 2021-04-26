package com.example.loginsesame.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query ("DELETE FROM USER WHERE id = 1 ")
    fun deleteAllUsers()

    @Query("SELECT * FROM USER")
    fun getAllUsers(): List<User>
}

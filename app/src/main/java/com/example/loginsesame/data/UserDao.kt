package com.example.loginsesame.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query ("DELETE FROM USER")
    fun deleteAllUsers()

    @Query("SELECT * FROM USER")
    fun getAllUsers(): Flow<List<User>>
}

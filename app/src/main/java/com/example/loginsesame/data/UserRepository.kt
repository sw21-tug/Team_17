package com.example.loginsesame.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao, private val vaultEntryDao: VaultEntryDao) {
    val users: Flow<List<User>> = userDao.getAllUsers()
    val entries: Flow<List<VaultEntry>> = vaultEntryDao.allEntries()

    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insertUser(user)
    }

    @WorkerThread
    suspend fun delete(user: User) {
        userDao.deleteUser(user)
    }

    @WorkerThread
    fun deleteAll(){
        userDao.deleteAllUsers()
    }

}

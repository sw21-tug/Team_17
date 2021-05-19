package com.example.loginsesame.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao, private val vaultEntryDao: VaultEntryDao) {
    val users: Flow<List<User>> = userDao.getAllUsers()
    val entries: Flow<List<VaultEntry>> = vaultEntryDao.allEntries()

    @WorkerThread
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    @WorkerThread
    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    @WorkerThread
    suspend fun insertVaultEntry(vaultEntry: VaultEntry){
        vaultEntryDao.add(vaultEntry)
    }

    @WorkerThread
    suspend fun deleteVaultEntry(vaultEntry: VaultEntry){
        vaultEntryDao.deleteVaultEntry(vaultEntry)
    }

    @WorkerThread
    fun deleteAll(){
        userDao.deleteAllUsers()
    }

}

package com.example.loginsesame.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VaultEntryDao {
    @Insert
    suspend fun add(newVaultEntry: VaultEntry)

    @Delete
    suspend fun deleteVaultEntry(vaultEntry: VaultEntry)

    @Query ("DELETE FROM VaultEntry")
    fun deleteAllEntries()

    @Query("SELECT * FROM VaultEntry")
    fun allEntries(): Flow<List<VaultEntry>>

    @Query("SELECT * FROM VaultEntry WHERE name = :askedName")
    fun getEntity(askedName : String): Flow<VaultEntry>

    @Query("SELECT * FROM VaultEntry WHERE Id = :accountId")
    fun getVaultData(accountId : Int): VaultEntry

    @Update
    suspend fun updateVaultEntry(vararg updated_entity: VaultEntry)

}


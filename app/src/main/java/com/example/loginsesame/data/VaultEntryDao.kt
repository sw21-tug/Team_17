package com.example.loginsesame.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VaultEntryDao {
    @Insert
    fun add(newVaultEntry: VaultEntry)

    @Delete
    fun deleteVaultEntry(vaultEntry: VaultEntry)

    @Query ("DELETE FROM VaultEntry")
    fun deleteAllEntries()

    @Query("SELECT * FROM VaultEntry")
    fun allEntries(): Flow<List<VaultEntry>>

    @Query("SELECT * FROM VaultEntry WHERE name = :asked_name")
    fun getEntity(asked_name : String): VaultEntry

    @Update
    fun updateVaultEntry(vararg updated_entity: VaultEntry)


}

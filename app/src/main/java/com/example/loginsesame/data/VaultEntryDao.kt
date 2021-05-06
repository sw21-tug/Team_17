package com.example.loginsesame.data

import androidx.room.*

@Dao
interface VaultEntryDao {
    @Insert
    fun add(newVaultEntry: VaultEntry)

    @Delete
    fun deleteVaultEntry(vaultEntry: VaultEntry)

    @Query ("DELETE FROM VaultEntry")
    fun deleteAllEntrys()

    @Query("SELECT * FROM VaultEntry")
    fun allEntrys(): List<VaultEntry>

    @Query("SELECT * FROM VaultEntry WHERE name = :asked_name")
    fun getEntity(asked_name : String): VaultEntry

    @Update
    fun updateVaultEntry(vararg updated_entity: VaultEntry)


}

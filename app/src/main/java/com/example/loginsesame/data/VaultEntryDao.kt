package com.example.loginsesame.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VaultEntryDao {
    @Insert
    fun add(newVaultEntry: VaultEntry)

    @Delete
    fun deleteVaultEntry(vaultEntry: VaultEntry)

    @Query ("DELETE FROM VaultEntry WHERE id = 1 ")
    fun deleteAllEntrys()

    @Query("SELECT * FROM VaultEntry")
    fun allEntrys(): List<VaultEntry>

    @Query("SELECT * FROM VaultEntry WHERE ")
}

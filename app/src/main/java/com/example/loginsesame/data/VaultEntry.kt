package com.example.loginsesame.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey

/*@Entity(foreignKeys = arrayOf(ForeignKey(entity = User::class,
        parentColumns = arrayOf("Id"),
        childColumns = arrayOf("UserID"),
        onDelete = ForeignKey.CASCADE)))*/
@Entity
data class VaultEntry(
    @PrimaryKey(autoGenerate = true) val Id : Int,
    val Name : String,
    val username: String,
    val url: String,
    val Password: String
    //@Ignore val UserID: Int
)

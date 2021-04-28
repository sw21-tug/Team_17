package com.example.loginsesame.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val Id: Int?,
    val Name: String,
    val Email: String,
    val Password: String
)

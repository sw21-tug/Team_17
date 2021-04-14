package com.example.loginsesame.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class user(
        @PrimaryKey val Id: Int,
        val Name: String,
        val Password: String,
        val Email: String
)

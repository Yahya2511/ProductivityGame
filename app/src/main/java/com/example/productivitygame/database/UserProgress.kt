package com.example.productivitygame.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey
    val id: Int = 1, //single user

    @ColumnInfo(name = "total_points", defaultValue = "0")
    val totalPoints: Int = 0
)
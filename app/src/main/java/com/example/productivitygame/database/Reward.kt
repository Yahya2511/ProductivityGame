package com.example.productivitygame.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rewards")
data class Reward(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "cost")
    val cost: Int,

    @ColumnInfo(name = "is_claimed", defaultValue = "0")
    val isClaimed: Boolean = false
)
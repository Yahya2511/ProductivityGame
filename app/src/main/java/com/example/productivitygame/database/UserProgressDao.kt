package com.example.productivitygame.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE id = 1")
    fun getProgress(): LiveData<UserProgress?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(progress: UserProgress)

    @Query("UPDATE user_progress SET total_points = total_points + 1 WHERE id = 1")
    suspend fun incrementPoints()

    @Query("UPDATE user_progress SET total_points = total_points - :cost WHERE id = 1")
    suspend fun deductPoints(cost: Int)
}
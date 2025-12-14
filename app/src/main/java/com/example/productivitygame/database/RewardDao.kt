package com.example.productivitygame.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RewardDao {
    @Query("SELECT * FROM rewards WHERE is_claimed = 0 ORDER BY cost ASC")
    fun getAvailableRewards(): LiveData<List<Reward>>

    @Insert
    suspend fun insert(reward: Reward)

    @Query("UPDATE rewards SET is_claimed = 1 WHERE id = :rewardId")
    suspend fun claimReward(rewardId: Long)
}
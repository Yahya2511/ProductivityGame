package com.example.productivitygame.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RewardDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var rewardDao: RewardDao
    private lateinit var supportDb: SupportSQLiteDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        rewardDao = db.rewardDao()
        supportDb = db.openHelper.writableDatabase
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    //1: inserting reward increases row count
    @Test
    fun insertReward() = runBlocking {
        val newReward = Reward(name = "New Reward", cost = 50)

        rewardDao.insert(newReward)

        val cursor = supportDb.query("SELECT * FROM rewards")
        val resultCount = cursor.count
        cursor.close()

        assertEquals(1, resultCount)
    }

    //2: new reward is marked as 'not claimed' by default
    @Test
    fun insertReward_isNotClaimed() = runBlocking {
        // Arrange
        val newReward = Reward(name = "Unclaimed Reward", cost = 25)

        rewardDao.insert(newReward)

        val cursor = supportDb.query("SELECT * FROM rewards WHERE is_claimed = 0")
        val resultCount = cursor.count
        cursor.close()

        assertEquals(1, resultCount)
    }


    //3: claiming a reward updates its status
    @Test
    fun claimReward() = runBlocking {
        // Arrange
        val rewardToClaim = Reward(id = 1, name = "Claimable Reward", cost = 75)
        rewardDao.insert(rewardToClaim)

        rewardDao.claimReward(1)

        val cursor = supportDb.query("SELECT * FROM rewards WHERE is_claimed = 0")
        val resultCount = cursor.count
        cursor.close()

        assertEquals(0, resultCount)
    }
}

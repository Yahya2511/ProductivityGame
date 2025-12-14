package com.example.productivitygame

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.productivitygame.database.Reward
import com.example.productivitygame.database.RewardDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class RewardDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockRewardDao: RewardDao

    @Before
    fun setUp() {
        mockRewardDao = mock(RewardDao::class.java)
    }

    @Test
    fun testGetAvailableRewards() {
        val rewardsList = listOf(Reward(id = 1, name = "Reward 10", cost = 100))
        val liveData = MutableLiveData<List<Reward>>().apply { value = rewardsList }
        `when`(mockRewardDao.getAvailableRewards()).thenReturn(liveData)

        val result = mockRewardDao.getAvailableRewards().value
        assertEquals(rewardsList, result)
    }

    @Test
    fun testInsertReward() = runBlocking {
        val newReward = Reward(name = "New Reward", cost = 50)
        mockRewardDao.insert(newReward)
        verify(mockRewardDao).insert(newReward)
    }

    @Test
    fun testClaimReward() = runBlocking {
        val rewardId = 1L
        mockRewardDao.claimReward(rewardId)
        verify(mockRewardDao).claimReward(rewardId)
    }

    @Test // returns empty list when no rewards
    fun testEmpty_GetAvailableRewards() {
        val liveData = MutableLiveData<List<Reward>>().apply { value = emptyList() }
        `when`(mockRewardDao.getAvailableRewards()).thenReturn(liveData)

        val result = mockRewardDao.getAvailableRewards().value
        assertEquals(emptyList<Reward>(), result)
    }
}

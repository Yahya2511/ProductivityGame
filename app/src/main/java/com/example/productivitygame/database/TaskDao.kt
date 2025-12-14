package com.example.productivitygame.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE is_completed = 0 ORDER BY id DESC")
    fun getActiveTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE is_completed = 1 ORDER BY id DESC")
    fun getCompletedTasks(): LiveData<List<Task>>

    @Insert
    suspend fun insert(task: Task)

    @Query("UPDATE tasks SET is_completed = 1 WHERE id = :taskId")
    suspend fun completeTask(taskId: Long)
}
package com.example.productivitygame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productivitygame.database.AppDatabase
import com.example.productivitygame.database.Task
import com.example.productivitygame.database.TaskDao

class QuestsActivity : AppCompatActivity() {

    private lateinit var taskDao: TaskDao
    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quests)

        taskDao = AppDatabase.getDatabase(applicationContext).taskDao()

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView)
        taskAdapter = TaskAdapter(emptyList())
        tasksRecyclerView.adapter = taskAdapter
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)

        taskDao.getActiveTasks().observe(this, Observer { tasks ->
            tasks?.let {
                taskAdapter.updateTasks(it)
            }
        })

        val btnAddTask = findViewById<Button>(R.id.btnAddTask)
        btnAddTask.setOnClickListener {
            val intent = Intent(this, AddQuestsActivity::class.java)
            startActivity(intent)
        }

        val questBtn = findViewById<LinearLayout>(R.id.Quest)
        val progressBtn = findViewById<LinearLayout>(R.id.Progress)
        val rewardsBtn = findViewById<LinearLayout>(R.id.Rewards)

        questBtn.setOnClickListener {
            if (this !is QuestsActivity) {
                val intent = Intent(this, QuestsActivity::class.java)
                startActivity(intent)
            }
        }

        progressBtn.setOnClickListener {
            if (this !is ProgressActivity) {
                val intent = Intent(this, ProgressActivity::class.java)
                startActivity(intent)
            }
        }

        rewardsBtn.setOnClickListener {
            if (this !is RewardsActivity) {
                val intent = Intent(this, RewardsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

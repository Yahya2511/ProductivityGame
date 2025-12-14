package com.example.productivitygame

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productivitygame.database.AppDatabase
import com.example.productivitygame.database.TaskDao
import com.example.productivitygame.database.UserProgressDao

class ProgressActivity : AppCompatActivity() {

    private lateinit var taskDao: TaskDao
    private lateinit var userProgressDao: UserProgressDao
    private lateinit var progressRecyclerView: RecyclerView
    private lateinit var progressAdapter: ProgressAdapter
    private lateinit var totalPointsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_progress)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_progress)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = AppDatabase.getDatabase(applicationContext)
        taskDao = db.taskDao()
        userProgressDao = db.userProgressDao()

        totalPointsTextView = findViewById(R.id.totalPoints)
        progressRecyclerView = findViewById(R.id.cardsRecycler)

        progressAdapter = ProgressAdapter(emptyList())
        progressRecyclerView.adapter = progressAdapter
        progressRecyclerView.layoutManager = LinearLayoutManager(this)

        taskDao.getCompletedTasks().observe(this, Observer { completedTasks ->
            completedTasks?.let {
                progressAdapter.updateTasks(it)
            }
        })

        userProgressDao.getProgress().observe(this, Observer { userProgress ->
            val points = userProgress?.totalPoints ?: 0
            totalPointsTextView.text = "Points: $points\n"
        })

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

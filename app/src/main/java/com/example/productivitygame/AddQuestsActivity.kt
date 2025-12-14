package com.example.productivitygame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.productivitygame.database.AppDatabase
import com.example.productivitygame.database.Task
import com.example.productivitygame.database.TaskDao
import kotlinx.coroutines.launch

class AddQuestsActivity : AppCompatActivity() {

    private lateinit var taskDao: TaskDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        taskDao = AppDatabase.getDatabase(applicationContext).taskDao()

        val btnSave = findViewById<Button>(R.id.addButton)
        val taskNameEditText = findViewById<EditText>(R.id.TaskName)

        btnSave.setOnClickListener {
            val taskName = taskNameEditText.text.toString().trim()

            if (taskName.isNotEmpty()) {
                lifecycleScope.launch {
                    val newTask = Task(name = taskName)
                    taskDao.insert(newTask)
                }
                finish()
            } else {
                Toast.makeText(this, "Please enter a task name", Toast.LENGTH_SHORT).show()
            }
        }


        val questBtn = findViewById<LinearLayout>(R.id.Quest)
        val progressBtn = findViewById<LinearLayout>(R.id.Progress)
        val rewardsBtn = findViewById<LinearLayout>(R.id.Rewards)

        questBtn.setOnClickListener {
            if(this !is QuestsActivity){
                val intent = Intent(this, QuestsActivity::class.java)
                startActivity(intent)
            }
        }

        progressBtn.setOnClickListener {
            if(this !is ProgressActivity){
                val intent = Intent(this, ProgressActivity::class.java)
                startActivity(intent)
            }
        }

        rewardsBtn.setOnClickListener {
             if(this !is RewardsActivity) {
                 val intent = Intent(this, RewardsActivity::class.java)
                 startActivity(intent)
             }
        }
    }
}

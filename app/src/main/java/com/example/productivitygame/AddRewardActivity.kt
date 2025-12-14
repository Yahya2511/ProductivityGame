package com.example.productivitygame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.productivitygame.database.AppDatabase
import com.example.productivitygame.database.Reward
import com.example.productivitygame.database.RewardDao
import kotlinx.coroutines.launch

class AddRewardActivity : AppCompatActivity() {

    private lateinit var rewardDao: RewardDao
    private lateinit var rewardNameEditText: EditText
    private lateinit var rewardCostEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_reward)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_add_reward)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rewardDao = AppDatabase.getDatabase(applicationContext).rewardDao()
        rewardNameEditText = findViewById(R.id.rewardName)
        rewardCostEditText = findViewById(R.id.rewardCost)

        val btnSave = findViewById<Button>(R.id.addButton)

        btnSave.setOnClickListener {
            saveReward()
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

    private fun saveReward() {
        val name = rewardNameEditText.text.toString()
        val costString = rewardCostEditText.text.toString()

        if (name.isBlank() || costString.isBlank()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val cost = costString.toIntOrNull()
        if (cost == null) {
            Toast.makeText(this, "Please enter a valid number for cost", Toast.LENGTH_SHORT).show()
            return
        }

        val reward = Reward(name = name, cost = cost)

        lifecycleScope.launch {
            rewardDao.insert(reward)
            finish()
        }
    }
}

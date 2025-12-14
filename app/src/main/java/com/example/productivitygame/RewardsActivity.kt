package com.example.productivitygame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productivitygame.database.AppDatabase
import com.example.productivitygame.database.RewardDao

class RewardsActivity : AppCompatActivity() {

    private lateinit var rewardDao: RewardDao
    private lateinit var rewardsRecyclerView: RecyclerView
    private lateinit var rewardAdapter: RewardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rewards)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_rewards)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rewardDao = AppDatabase.getDatabase(applicationContext).rewardDao()

        rewardsRecyclerView = findViewById(R.id.rewardsRecycler)
        rewardAdapter = RewardAdapter(emptyList())
        rewardsRecyclerView.adapter = rewardAdapter
        rewardsRecyclerView.layoutManager = LinearLayoutManager(this)

        rewardDao.getAvailableRewards().observe(this, Observer { rewards ->
            rewards?.let {
                rewardAdapter.updateRewards(it)
            }
        })

        val btnAddReward = findViewById<Button>(R.id.btnAddReward)
        btnAddReward.setOnClickListener {
            val intent = Intent(this, AddRewardActivity::class.java)
            startActivity(intent)
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

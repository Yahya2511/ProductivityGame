package com.example.productivitygame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productivitygame.database.AppDatabase
import com.example.productivitygame.database.Reward
import com.example.productivitygame.database.RewardDao
import com.example.productivitygame.database.UserProgressDao
import kotlinx.coroutines.launch

class RewardsActivity : AppCompatActivity() {

    private lateinit var rewardDao: RewardDao
    private lateinit var userProgressDao: UserProgressDao
    private lateinit var rewardsRecyclerView: RecyclerView
    private lateinit var rewardAdapter: RewardAdapter
    private lateinit var totalPointsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rewards)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_rewards)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = AppDatabase.getDatabase(applicationContext)
        rewardDao = db.rewardDao()
        userProgressDao = db.userProgressDao()

        totalPointsTextView = findViewById(R.id.totalPoints)
        rewardsRecyclerView = findViewById(R.id.rewardsRecycler)

        setupRecyclerView()
        observeRewards()
        observeUserProgress()

        val btnAddReward = findViewById<Button>(R.id.btnAddReward)
        btnAddReward.setOnClickListener {
            val intent = Intent(this, AddRewardActivity::class.java)
            startActivity(intent)
        }

        setupBottomNavigation()
    }

    private fun setupRecyclerView() {
        rewardAdapter = RewardAdapter(emptyList()) { reward ->
            handleRewardClick(reward)
        }
        rewardsRecyclerView.adapter = rewardAdapter
        rewardsRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeRewards() {
        rewardDao.getAvailableRewards().observe(this, Observer { rewards ->
            rewards?.let {
                rewardAdapter.updateRewards(it)
            }
        })
    }

    private fun observeUserProgress() {
        userProgressDao.getProgress().observe(this, Observer { progress ->
            progress?.let {
                totalPointsTextView.text = "Total Points: ${it.totalPoints}"
            }
        })
    }

    private fun handleRewardClick(reward: Reward) {
        lifecycleScope.launch {
            val userProgress = userProgressDao.getProgressSynchronously()
            val currentPoints = userProgress?.totalPoints ?: 0

            if (currentPoints >= reward.cost) {
                showConfirmationDialog(reward)
            } else {
                showInsufficientPointsDialog()
            }
        }
    }

    private fun showConfirmationDialog(reward: Reward) {
        AlertDialog.Builder(this)
            .setTitle("Confirm Purchase")
            .setMessage("Are you sure you want to claim '${reward.name}' for ${reward.cost} points?")
            .setPositiveButton("Yes") { _, _ ->
                claimReward(reward)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun showInsufficientPointsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Oops")
            .setMessage("You do not have enough points to claim this reward.")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun claimReward(reward: Reward) {
        lifecycleScope.launch {
            userProgressDao.deductPoints(reward.cost)
            rewardDao.claimReward(reward.id)
            Toast.makeText(this@RewardsActivity, "'${reward.name}' claimed!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBottomNavigation() {
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

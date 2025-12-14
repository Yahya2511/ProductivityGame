package com.example.productivitygame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddRewardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_reward)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_add_reward)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSave = findViewById<Button>(R.id.addButton)

        btnSave.setOnClickListener {
            finish()
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
package com.example.productivitygame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.productivitygame.database.Reward

class RewardAdapter(private var rewards: List<Reward>) : RecyclerView.Adapter<RewardAdapter.RewardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reward_card, parent, false)
        return RewardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RewardViewHolder, position: Int) {
        val reward = rewards[position]
        holder.rewardTitle.text = reward.name
        holder.rewardPoints.text = "${reward.cost} Points"
    }

    override fun getItemCount() = rewards.size

    class RewardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rewardTitle: TextView = itemView.findViewById(R.id.item_title)
        val rewardPoints: TextView = itemView.findViewById(R.id.item_points)
    }

    fun updateRewards(newRewards: List<Reward>) {
        rewards = newRewards
        notifyDataSetChanged()
    }
}

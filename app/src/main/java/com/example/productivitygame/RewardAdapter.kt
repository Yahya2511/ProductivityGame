package com.example.productivitygame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.productivitygame.database.Reward


class RewardAdapter(
    private var rewards: List<Reward>,
    private val onItemClicked: (Reward) -> Unit
) : RecyclerView.Adapter<RewardAdapter.RewardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reward_card, parent, false)
        return RewardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RewardViewHolder, position: Int) {
        val reward = rewards[position]
        holder.bind(reward)
        holder.itemView.setOnClickListener {
            onItemClicked(reward)
        }
    }

    override fun getItemCount() = rewards.size

    class RewardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rewardTitle: TextView = itemView.findViewById(R.id.item_title)
        private val rewardPoints: TextView = itemView.findViewById(R.id.item_points)

        fun bind(reward: Reward) {
            rewardTitle.text = reward.name
            rewardPoints.text = "${reward.cost} Points"
        }
    }

    fun updateRewards(newRewards: List<Reward>) {
        rewards = newRewards
        notifyDataSetChanged()
    }
}

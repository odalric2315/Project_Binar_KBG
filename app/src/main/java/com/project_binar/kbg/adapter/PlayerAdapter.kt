package com.project_binar.kbg.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project_binar.kbg.R
import com.project_binar.kbg.databinding.ListLeaderboardBinding
import com.project_binar.kbg.model.Player
import kotlin.math.roundToInt


class PlayerAdapter(val data: List<Player>) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    lateinit var onClick: (Player) -> Unit

    fun setOnClickItemListener(onClick: (Player) -> Unit) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ListLeaderboardBinding = ListLeaderboardBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerAdapter.ViewHolder, position: Int) {
        holder.bindViewHolder(data[position], position)
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(var binding: ListLeaderboardBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindViewHolder(player: Player, position: Int){
            binding.tvPeringkat.text = "${position+1}"
            binding.tvNamaLeaderboard.text = player.nama
            binding.tvWinrateLeaderboard.text = "${player.rate?.roundToInt().toString().trim()} %"
            when (position) {
                0 -> {
                    binding.starLeaderboard.setImageResource(R.drawable.star_1)
                }
                1 -> {
                    binding.starLeaderboard.setImageResource(R.drawable.star_2)
                }
                2 -> {
                    binding.starLeaderboard.setImageResource(R.drawable.star_3)
                }
            }
            binding.root.setOnClickListener {
                onClick(player)
            }
        }
    }

}
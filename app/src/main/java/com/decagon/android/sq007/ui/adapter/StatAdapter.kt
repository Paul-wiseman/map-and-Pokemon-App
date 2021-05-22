package com.decagon.android.sq007.ui.adapter

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.decagon.android.sq007.R
import com.decagon.android.sq007.pokemon.Stat

class StatAdapter(var statList: List<Stat>) : RecyclerView.Adapter<StatAdapter.StatsViewHolder>() {

    inner class StatsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val progressBar_learn_at1: ProgressBar = view.findViewById(R.id.progressBar_learn_at)
        val progressBar_learn_at2: ProgressBar = view.findViewById(R.id.progressBar_learn_at2)
        val progressBar_learn_at3: ProgressBar = view.findViewById(R.id.progressBar_learn_at3)
        val progressBar_learn_at4: ProgressBar = view.findViewById(R.id.progressBar_learn_at4)
        val progressBar_learn_at5: ProgressBar = view.findViewById(R.id.progressBar_learn_at5)
        val progressBar_learn_at6: ProgressBar = view.findViewById(R.id.progressBar_learn_at7)
        val progressBar_learn_at7: ProgressBar = view.findViewById(R.id.progressBar_learn_at8)
        val progressBar_learn_at8: ProgressBar = view.findViewById(R.id.progressBar_learn_at9)
        val progressBar_learn_at9: ProgressBar = view.findViewById(R.id.progressBar_learn_at10)
        val progressBar_learn_at10: ProgressBar = view.findViewById(R.id.progressBar_learn_at11)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatAdapter.StatsViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.stat_item, parent, false)
        return StatsViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return statList.size
    }

    override fun onBindViewHolder(holder: StatAdapter.StatsViewHolder, position: Int) {
        holder.progressBar_learn_at1.max = 1000
        holder.progressBar_learn_at2.max = 1000
        holder.progressBar_learn_at3.max = 1000
        holder.progressBar_learn_at4.max = 1000
        holder.progressBar_learn_at5.max = 1000
        holder.progressBar_learn_at6.max = 1000
        holder.progressBar_learn_at7.max = 1000
        holder.progressBar_learn_at8.max = 1000
        holder.progressBar_learn_at9.max = 1000
        holder.progressBar_learn_at10.max = 1000
        val rnds = (0..600).random()
        ObjectAnimator.ofInt(holder.progressBar_learn_at1, "progress", rnds)
            .setDuration(2000).start()
        ObjectAnimator.ofInt(holder.progressBar_learn_at2, "progress", rnds)
            .setDuration(2000).start()
        ObjectAnimator.ofInt(holder.progressBar_learn_at3, "progress", rnds)
            .setDuration(2000).start()
        ObjectAnimator.ofInt(holder.progressBar_learn_at4, "progress", rnds)
            .setDuration(2000).start()
        ObjectAnimator.ofInt(holder.progressBar_learn_at5, "progress", rnds)
            .setDuration(2000).start()
        ObjectAnimator.ofInt(holder.progressBar_learn_at6, "progress", rnds)
            .setDuration(2000).start()
        ObjectAnimator.ofInt(holder.progressBar_learn_at7, "progress", rnds)
            .setDuration(2000).start()
        ObjectAnimator.ofInt(holder.progressBar_learn_at8, "progress", rnds)
            .setDuration(2000).start()
        ObjectAnimator.ofInt(holder.progressBar_learn_at9, "progress", rnds)
            .setDuration(2000).start()
        ObjectAnimator.ofInt(holder.progressBar_learn_at10, "progress", rnds)
            .setDuration(2000).start()
    }
}

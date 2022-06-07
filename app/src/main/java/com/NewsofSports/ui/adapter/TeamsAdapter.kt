package com.NewsofSports.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.NewsofSports.R
import com.NewsofSports.data.model.match.Home
import com.NewsofSports.utility.OnItemClickListener


class TeamsAdapter(private val onItemClickListener: OnItemClickListener, val context: Context) :
    RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    private var teamList = emptyList<Home>()

    class TeamsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val teamName: TextView = view.findViewById(R.id.teamNameTV)
        val llTeam: LinearLayout = view.findViewById(R.id.llTeam)
        fun bind(match: Home, context: Context) {
            teamName.text = match.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_team, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        val item = teamList[position]

        holder.bind(item, context)

        holder.llTeam.setOnClickListener {
            onItemClickListener.onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    fun addTeamList(teams: List<Home>) {
        teamList = teams
        notifyDataSetChanged()
    }

}
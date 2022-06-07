package com.NewsofSports.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.NewsofSports.R
import com.NewsofSports.data.model.match.Home
import com.NewsofSports.utility.OnItemClickListener


class TeamsAdapter(private val onItemClickListener: OnItemClickListener, val context: Context) :
    RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    private var teamList = emptyList<Home>()

    class TeamsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teamName: TextView = view.findViewById(R.id.teamNameTV)
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
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    fun addNewsList(teams: List<Home>) {
        teamList = teams
        notifyDataSetChanged()
    }

}
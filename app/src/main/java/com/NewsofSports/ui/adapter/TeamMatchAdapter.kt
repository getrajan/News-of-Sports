package com.NewsofSports.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.NewsofSports.R
import com.NewsofSports.data.model.match.Match


class TeamMatchAdapter(val context: Context, val teamId: String) :
    RecyclerView.Adapter<TeamMatchAdapter.TeamMatchHolder>() {

    private var matchList = emptyList<Match>()

    class TeamMatchHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hostOrGuestTV: TextView = view.findViewById(R.id.hostOrGuestTV)
        val statusTV: TextView = view.findViewById(R.id.statusTV)
        val scoreTV: TextView = view.findViewById(R.id.scoreTV)
        val againstTeamNameTV: TextView = view.findViewById(R.id.againstTeamNameTV)
        fun bind(match: Match, context: Context) {
            scoreTV.text = match.score
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamMatchHolder {
        return TeamMatchHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_team_match_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TeamMatchHolder, position: Int) {
        val item = matchList[position]

        holder.bind(item, context)
        val goals = item.score.split(":")
        var homeTeamGoal = goals[0].toInt()
        var awayTeamGoal = goals[1].toInt()
        var currentTeam = CurrentTeamEnum.HOME
        if (teamId == item.home.id) {
            holder.hostOrGuestTV.text = "H"
            holder.againstTeamNameTV.text = item.away.name
            currentTeam = CurrentTeamEnum.HOME
        } else {
            holder.hostOrGuestTV.text = "A"
            holder.againstTeamNameTV.text = item.home.name
            currentTeam = CurrentTeamEnum.AWAY
        }

        if (homeTeamGoal == awayTeamGoal) {
            holder.statusTV.text = "D"
            holder.statusTV.setBackgroundResource(R.drawable.bg_drawn_status)
        } else if (homeTeamGoal > awayTeamGoal) {
            if (currentTeam == CurrentTeamEnum.HOME) {
                holder.statusTV.text = "W"
                holder.statusTV.setBackgroundResource(R.drawable.bg_win_status)

            } else {
                holder.statusTV.text = "L"
                holder.statusTV.setBackgroundResource(R.drawable.bg_lost_status)

            }
        } else {
            if (currentTeam == CurrentTeamEnum.AWAY) {
                holder.statusTV.text = "W"
                holder.statusTV.setBackgroundResource(R.drawable.bg_win_status)

            } else {
                holder.statusTV.text = "L"
                holder.statusTV.setBackgroundResource(R.drawable.bg_lost_status)
            }
        }
    }

    override fun getItemCount(): Int {
        return matchList.size
    }

    fun addMatchList(matches: List<Match>) {
        matchList = matches
        notifyDataSetChanged()
    }

}

enum class CurrentTeamEnum { HOME, AWAY }
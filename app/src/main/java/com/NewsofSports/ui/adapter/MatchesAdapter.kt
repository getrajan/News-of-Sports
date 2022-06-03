package com.NewsofSports.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.NewsofSports.R
import com.NewsofSports.data.model.match.Match
import com.NewsofSports.data.model.match.MatchEnum
import com.NewsofSports.utility.MyDateTimeFormat
import com.NewsofSports.utility.OnItemClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class MatchesAdapter(private val onItemClickListener: OnItemClickListener, val context: Context) :
    RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder>() {

    private var matchList = emptyList<Match>()


    class MatchesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val matchTypeTv: TextView = view.findViewById(R.id.matchTypeTV)
        private val matchTimerTv: TextView = view.findViewById(R.id.timerTV)
        private val team1Img: ImageView = view.findViewById(R.id.team1IconIV)
        private val team2Img: ImageView = view.findViewById(R.id.team2IconIV)
        private val team1Name: TextView = view.findViewById(R.id.team1NameTV)
        private val team2Name: TextView = view.findViewById(R.id.team2NameTV)

        @SuppressLint("SetTextI18n")
        fun bind(match: Match, context: Context) {
            if (match.matchType == MatchEnum.LIVE) {
                matchTypeTv.text = "LIVE"
                matchTypeTv.setTextColor(ContextCompat.getColor(context, R.color.red_color))
                matchTypeTv.textSize = 12f
                val typeface = ResourcesCompat.getFont(context, R.font.poppins_bold)
                matchTypeTv.typeface = typeface
                matchTimerTv.text = match.timer
            } else {
                matchTypeTv.text = MyDateTimeFormat.dateFormat(match.time.toLong() * 1000)
                matchTypeTv.setTextColor(ContextCompat.getColor(context, R.color.gray_color))
                val typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
                matchTypeTv.typeface = typeface
                matchTimerTv.text = MyDateTimeFormat.timeFormat(match.time.toLong() * 1000)
            }


            val team1ImgURL = "https://spoyer.com/api/team_img/soccer/${match.home.id}.png"
            val team2ImgURL = "https://spoyer.com/api/team_img/soccer/${match.away.id}.png"
            val options = RequestOptions()
                .placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_photo)

            Glide.with(context).load(team1ImgURL).apply(options).into(team1Img)
            Glide.with(context).load(team2ImgURL).apply(options).into(team2Img)

            team1Name.text = match.home.name
            team2Name.text = match.away.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        return MatchesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_match, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        val item = matchList[position]

        holder.bind(item, context)
    }

    override fun getItemCount(): Int {
        return matchList.size
    }

    fun addNewsList(matches: List<Match>) {
        matchList = matches
        notifyDataSetChanged()
    }


}
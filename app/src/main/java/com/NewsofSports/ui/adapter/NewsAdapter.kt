package com.NewsofSports.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.NewsofSports.R
import com.NewsofSports.data.model.news.News
import com.NewsofSports.utility.OnItemClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class NewsAdapter(val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var newsList = emptyList<News>()

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val newsTitle: TextView = view.findViewById(R.id.newsTitle)
        private val newsImage: ImageView = view.findViewById(R.id.newsImage)

        fun bind(news: News) {
            newsTitle.text = news.title
            val options = RequestOptions()
                .placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_photo)
            Glide.with(itemView).load(news.social_image).apply(options).into(newsImage)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = newsList[position]
        holder.newsTitle.setOnClickListener {
            onItemClickListener.onItemClick(item)
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun addNewsList(newsLst: List<News>) {
        newsList = newsLst
        notifyDataSetChanged()
    }


}
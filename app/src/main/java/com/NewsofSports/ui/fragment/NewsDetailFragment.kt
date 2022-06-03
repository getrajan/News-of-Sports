package com.NewsofSports.ui.fragment

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.NewsofSports.R
import com.NewsofSports.data.model.news.News
import com.bumptech.glide.Glide


class NewsDetailFragment : Fragment() {

    private lateinit var news: News
    private val args: NewsDetailFragmentArgs by navArgs<NewsDetailFragmentArgs>()
    private lateinit var newsIV: ImageView
    private lateinit var newsTitle: TextView
    private lateinit var newsContentHtml: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        news = args.news
        initView()
        setViews()
        this.requireActivity().actionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_back_arrow)
            setDisplayHomeAsUpEnabled(true)
        }
    }


    private fun initView() {
        newsIV = view?.findViewById(R.id.newsImage)!!
        newsTitle = view?.findViewById(R.id.newsTitle)!!
        newsContentHtml = view?.findViewById(R.id.htmlTextView)!!

    }

    private fun setViews() {
        Glide.with(this).load(news.social_image).into(newsIV)
        newsTitle.text = news.title

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newsContentHtml.text = Html.fromHtml(
                news.content, HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        } else {
            newsContentHtml.text = Html.fromHtml(news.content)
        }
    }

}
package com.NewsofSports.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.NewsofSports.R
import com.NewsofSports.api.ApiBuilder
import com.NewsofSports.api.ApiHelper
import com.NewsofSports.data.model.news.News
import com.NewsofSports.data.model.news.NewsResponse
import com.NewsofSports.ui.adapter.NewsAdapter
import com.NewsofSports.utility.OnItemClickListener
import com.NewsofSports.viewmodel.NewsViewModel
import com.NewsofSports.viewmodel.NewsViewModelFactory
import com.pegotec.retrofit_coroutine.utils.Status

class NewsFragment : Fragment() {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsRV: RecyclerView
    private lateinit var newsProgressBar: ProgressBar
    private lateinit var newsErrorTV: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        initViewModel()

        setObservers()


    }

    private fun initViews() {
        newsRV = view?.findViewById(R.id.newsRecyclerView)!!
        newsProgressBar = view?.findViewById(R.id.newsProgressBar)!!
        newsErrorTV = view?.findViewById(R.id.newsErrorText)!!
    }


    private fun initViewModel() {
        newsViewModel = ViewModelProvider(
            this,
            NewsViewModelFactory(
                ApiHelper(ApiBuilder.apiServiceForNews),
            )
        ).get(NewsViewModel::class.java)
    }

    private fun setObservers() {
        newsViewModel.getNewsResponse.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        newsProgressBar.visibility = View.VISIBLE
                        newsRV.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        newsProgressBar.visibility = View.GONE
                        newsRV.visibility = View.GONE
                        newsErrorTV.visibility = View.VISIBLE
                        newsErrorTV.text = it.message
                    }
                    Status.SUCCESS -> {
                        val newsResponse: NewsResponse? = it.data
                        newsProgressBar.visibility = View.GONE
                        newsRV.visibility = View.VISIBLE
                        newsRV.layoutManager = LinearLayoutManager(requireContext())
                        val newsAdapter = NewsAdapter(object : OnItemClickListener {
                            override fun onItemClick(units: Any) {
                                val action =
                                    NewsFragmentDirections.actionNavNewsToNewsDetailFragment(news = units as News)
                                findNavController().navigate(action)

                            }
                        })
                        newsRV.adapter = newsAdapter
                        newsAdapter.addNewsList(newsLst = newsResponse!!.news)
                    }
                }
            }
        })
    }


}
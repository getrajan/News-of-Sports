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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.NewsofSports.R
import com.NewsofSports.api.ApiBuilder
import com.NewsofSports.api.ApiHelper
import com.NewsofSports.data.model.match.Match
import com.NewsofSports.ui.adapter.MatchesAdapter
import com.NewsofSports.utility.OnItemClickListener
import com.NewsofSports.viewmodel.MatchesViewModel
import com.NewsofSports.viewmodel.MatchesViewModelFactory
import com.pegotec.retrofit_coroutine.utils.Status

class MatchesFragment : Fragment() {

    private lateinit var matchesViewModel: MatchesViewModel
    private lateinit var matchesProgressbar: ProgressBar
    private lateinit var matchesErrorTV: TextView
    private lateinit var matchesRV: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initViewModel()
        setObserver()

    }

    private fun initViews() {
        matchesProgressbar = view?.findViewById(R.id.matchProgressbar)!!
        matchesErrorTV = view?.findViewById(R.id.matchErrorTV)!!
        matchesRV = view?.findViewById(R.id.matchesRV)!!
    }


    private fun initViewModel() {
        matchesViewModel = ViewModelProvider(
            this,
            MatchesViewModelFactory(
                ApiHelper(ApiBuilder.apiService),
            )
        ).get(MatchesViewModel::class.java)
    }

    private fun setObserver() {
        matchesViewModel.getMatchesResponse.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        matchesProgressbar.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        matchesProgressbar.visibility = View.GONE
                        matchesErrorTV.visibility = View.VISIBLE
                        matchesErrorTV.text = it.message
                    }
                    Status.SUCCESS -> {
                        matchesProgressbar.visibility = View.GONE

                        matchesRV.layoutManager = LinearLayoutManager(requireContext())
                        val matchesAdapter = MatchesAdapter(object : OnItemClickListener {
                            override fun onItemClick(units: Any) {
//                                val action =
//                                    NewsFragmentDirections.actionNavNewsToNewsDetailFragment(news = units as News)
//                                findNavController().navigate(action)

                            }
                        }, requireContext())
                        matchesRV.adapter = matchesAdapter
                        matchesAdapter.addNewsList(matches = it.data as List<Match>)
                    }
                }

            }
        })
    }
}
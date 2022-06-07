package com.NewsofSports.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.NewsofSports.R
import com.NewsofSports.api.ApiBuilder
import com.NewsofSports.api.ApiHelper
import com.NewsofSports.data.model.match.Home
import com.NewsofSports.data.model.match.Match
import com.NewsofSports.ui.adapter.TeamMatchAdapter
import com.NewsofSports.viewmodel.TeamViewModel
import com.NewsofSports.viewmodel.TeamViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pegotec.retrofit_coroutine.utils.Status
import kotlinx.coroutines.launch


class TeamFragment : Fragment() {
    private lateinit var teamViewModel: TeamViewModel
    private lateinit var teamProgressbar: ProgressBar
    private lateinit var teamErrorTV: TextView
    private lateinit var teamMatchesRV: RecyclerView
    private lateinit var teamNameTV: TextView
    private lateinit var teamDetailTeamIV: ImageView
    private val args: TeamFragmentArgs by navArgs<TeamFragmentArgs>()
    private lateinit var team: Home


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        team = args.team

        initViews()

        setUpViews()

        initViewModel()

        setupObserver()
    }

    private fun setUpViews() {
        val teamImageURL = "https://spoyer.com/api/team_img/soccer/${team.id}.png"
        val options = RequestOptions()
            .placeholder(R.drawable.ic_photo)
            .error(R.drawable.ic_photo)
        Glide.with(requireContext()).load(teamImageURL).apply(options).into(teamDetailTeamIV)

        teamNameTV.text = team.name
    }


    private fun initViews() {
        teamProgressbar = view?.findViewById(R.id.teamDetailProgressbar)!!
        teamMatchesRV = view?.findViewById(R.id.teamDetailEventsRV)!!
        teamErrorTV = view?.findViewById(R.id.teamDetailErrorTV)!!
        teamNameTV = view?.findViewById(R.id.teamDetailTeamNameTV)!!
        teamDetailTeamIV = view?.findViewById(R.id.teamDetailTeamIV)!!
    }

    private fun initViewModel() {
        teamViewModel = ViewModelProvider(
            this,
            TeamViewModelFactory(
                ApiHelper(ApiBuilder.apiService),
            )
        ).get(TeamViewModel::class.java)

        lifecycleScope.launch {
            teamViewModel.getTeamMatches(team = team.id)
        }
    }

    private fun setupObserver() {
        teamViewModel.getTeamDetailResponse.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        teamProgressbar.visibility = View.VISIBLE

                    }
                    Status.ERROR -> {
                        teamProgressbar.visibility = View.GONE
                        teamErrorTV.visibility = View.VISIBLE
                        teamErrorTV.text = resource.message
                    }
                    Status.SUCCESS -> {
                        teamProgressbar.visibility = View.GONE
                        teamMatchesRV.layoutManager = LinearLayoutManager(requireContext())
                        val teamMatchAdapter = TeamMatchAdapter(
                            requireContext(),
                            team.id
                        )
                        teamMatchesRV.adapter = teamMatchAdapter
                        teamMatchAdapter.addMatchList(matches = it.data?.games_end as List<Match>)
                    }
                }
            }
        })
    }
}
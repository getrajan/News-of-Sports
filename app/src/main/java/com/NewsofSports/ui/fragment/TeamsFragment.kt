package com.NewsofSports.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import com.NewsofSports.data.model.match.Home
import com.NewsofSports.ui.adapter.TeamsAdapter
import com.NewsofSports.utility.OnItemClickListener
import com.NewsofSports.viewmodel.TeamsViewModel
import com.NewsofSports.viewmodel.TeamsViewModelFactory
import com.pegotec.retrofit_coroutine.utils.Status


class TeamsFragment : Fragment() {
    private lateinit var teamsViewModel: TeamsViewModel
    private lateinit var teamsRV: RecyclerView
    private lateinit var searchET: EditText
    private lateinit var teamsProgressbar: ProgressBar
    private lateinit var teamsErrorTV: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        initViewModel()

        setObservers()

        searchTeam()
    }


    private fun initViews() {
        teamsErrorTV = view?.findViewById(R.id.teamsErrorTV)!!
        teamsProgressbar = view?.findViewById(R.id.teamsProgressbar)!!
        teamsRV = view?.findViewById(R.id.teamsRV)!!
        searchET = view?.findViewById(R.id.teamSearchET)!!
    }

    private fun initViewModel() {
        teamsViewModel = ViewModelProvider(
            this,
            TeamsViewModelFactory(
                ApiHelper(ApiBuilder.apiService),
            )
        ).get(TeamsViewModel::class.java)
    }

    private fun searchTeam() {
        searchET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
                teamsViewModel.searchTeam(char ?: "")

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun setObservers() {
        teamsViewModel.getTeamsResponse.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        teamsProgressbar.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        teamsProgressbar.visibility = View.GONE
                        teamsErrorTV.visibility = View.VISIBLE
                        teamsErrorTV.text = it.message
                    }
                    Status.SUCCESS -> {
                        teamsProgressbar.visibility = View.GONE

                        teamsRV.layoutManager = LinearLayoutManager(requireContext())
                        val teamsAdapter = TeamsAdapter(object : OnItemClickListener {
                            override fun onItemClick(units: Any) {
                                val action =
                                    TeamsFragmentDirections.actionNavTeamsToTeamFragment(team = units as Home)
                                findNavController().navigate(action)
                            }
                        }, requireContext())
                        teamsRV.adapter = teamsAdapter
                        teamsAdapter.addTeamList(teams = it.data as List<Home>)
                    }
                }

            }
        })
    }

}
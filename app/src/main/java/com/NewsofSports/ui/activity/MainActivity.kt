package com.NewsofSports.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.NewsofSports.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navGraph: NavGraph
    private lateinit var navController: NavController
    private lateinit var toolBar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolBar = findViewById(R.id.toolBar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_host) as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        navGraph = graphInflater.inflate(R.navigation.main_nav)
        navController = navHostFragment.navController
        setSupportActionBar(toolBar)

        //setup menu
        setupDrawer()

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.main_fragment_host)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupDrawer() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val drawerNavView: NavigationView = findViewById(R.id.navigationView)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navNews,
                R.id.navTeams,
                R.id.navMatches
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        drawerNavView.setupWithNavController(navController)
        drawerNavView.setNavigationItemSelectedListener(this)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navNews -> {
                navigateToNews()
            }
            R.id.navTeams -> {
                navigateToTeams()
            }
            R.id.navMatches -> {
                navigateToMatches()
            }
        }
        return true
    }

    @SuppressLint("RtlHardcoded")
    private fun navigateToMatches() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.closeDrawer(Gravity.LEFT)
        findNavController(R.id.main_fragment_host).navigate(R.id.navMatches)
    }

    @SuppressLint("RtlHardcoded")
    private fun navigateToTeams() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.closeDrawer(Gravity.LEFT)
        findNavController(R.id.main_fragment_host).navigate(R.id.navTeams)
    }

    @SuppressLint("RtlHardcoded")
    private fun navigateToNews() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.closeDrawer(Gravity.LEFT)
        findNavController(R.id.main_fragment_host).navigate(R.id.navNews)
    }
}
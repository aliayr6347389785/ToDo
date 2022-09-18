package com.uli.todo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uli.todo.databinding.ActivityMainBinding
import com.uli.todo.ui.App


class MainActivity : AppCompatActivity() {

    private lateinit var context:MainActivity
    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navView = binding.navView
        initNavController()
    }

    fun initNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(com.uli.todo.R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                com.uli.todo.R.id.navigation_home,
                com.uli.todo.R.id.navigation_dashboard,
                com.uli.todo.R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (!App.prefs.isBoardShow()) {
            navController.navigate(com.uli.todo.R.id.onBoardFragment)

        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val list: ArrayList<Int> = ArrayList()
            list.add(com.uli.todo.R.id.navigation_home)
            list.add(com.uli.todo.R.id.navigation_dashboard)
            list.add(com.uli.todo.R.id.navigation_notifications)
            if (list.contains(destination.id)) {
                binding.navView.isVisible = true
            } else {
                binding.navView.isGone = true
            }
        }
    }
}
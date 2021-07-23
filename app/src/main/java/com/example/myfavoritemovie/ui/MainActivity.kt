package com.example.myfavoritemovie.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.myfavoritemovie.R
import com.example.myfavoritemovie.data.service.Actions
import com.example.myfavoritemovie.data.service.NetworkChangeReceiver
import com.example.myfavoritemovie.data.service.UpcomingMoviesReceiver
import com.example.myfavoritemovie.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val receiver = NetworkChangeReceiver()
    private val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
    private lateinit var bottomNavigationView: BottomNavigationView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MyFavoriteMovie)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = binding.bottomNavigationMenu
        auth = Firebase.auth
        Log.wtf("MY_APP_FIREBASE_USER", "${auth.currentUser}")
        setUpNavigation()
        Intent(this, UpcomingMoviesReceiver::class.java).also {
            it.action = Actions.START.name
            startForegroundService(it)
        }
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(
            bottomNavigationView,
            navHostFragment!!.navController
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null) {
            auth.signInAnonymously()
        }
    }

    override fun onResume() {
        registerReceiver(receiver, filter)
        super.onResume()
    }

    override fun onPause() {
        unregisterReceiver(receiver)
        super.onPause()
    }

    fun getExit(view: View) {
        exitProcess(-1)
    }
}
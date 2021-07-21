package com.example.myfavoritemovie.ui

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myfavoritemovie.R
import com.example.myfavoritemovie.app.NetworkChangeReceiver
import com.example.myfavoritemovie.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val receiver = NetworkChangeReceiver()
    private val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MyFavoriteMovie)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        Log.wtf("MY_APP_FIREBASE_USER", "${auth.currentUser}")
    }

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
}
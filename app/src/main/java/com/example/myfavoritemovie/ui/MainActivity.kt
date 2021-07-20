package com.example.myfavoritemovie.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myfavoritemovie.R
import com.example.myfavoritemovie.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

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
}
package com.example.myfavoritemovie.app

import android.app.Application
import android.content.Context
import com.example.myfavoritemovie.app.dependency.MoviesModule
import com.example.myfavoritemovie.app.dependency.SearchModule
import com.example.myfavoritemovie.app.dependency.UpcomingModule
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class App : Application() {
    val moviesModule by lazy { MoviesModule() }
    val searchModule by lazy { SearchModule(moviesModule) }
    val upcomingModule by lazy { UpcomingModule(moviesModule) }

    override fun onCreate() {
        super.onCreate()
        Firebase.database.setPersistenceEnabled(true)
    }
}

val Context.app: App
    get() = applicationContext as App
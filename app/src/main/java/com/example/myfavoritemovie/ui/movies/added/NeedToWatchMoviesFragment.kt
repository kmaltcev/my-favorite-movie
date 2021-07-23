package com.example.myfavoritemovie.ui.movies.added

import com.example.myfavoritemovie.data.source.firebase.FirebaseRealtimeDatabase
import com.google.firebase.database.Query

class NeedToWatchMoviesFragment : AddedMoviesFragment() {

    override fun getMoviesQuery(database: FirebaseRealtimeDatabase): Query {
        return database.needToWatchMovies
    }

    override fun getTitle(): String = "Need to watch movies"
}
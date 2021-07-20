package com.example.myfavoritemovie.ui.movies.added

import com.google.firebase.database.Query
import com.example.myfavoritemovie.data.source.firebase.FirebaseRealtimeDatabase

class GoodMoviesFragment : AddedMoviesFragment() {

    override fun getMoviesQuery(database: FirebaseRealtimeDatabase): Query {
        return database.goodMovies
    }

    override fun getTitle(): String = "Good Movies"
}
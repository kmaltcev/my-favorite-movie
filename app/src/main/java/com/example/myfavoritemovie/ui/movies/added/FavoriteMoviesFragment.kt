package com.example.myfavoritemovie.ui.movies.added

import com.example.myfavoritemovie.data.source.firebase.FirebaseRealtimeDatabase
import com.google.firebase.database.Query

class FavoriteMoviesFragment : AddedMoviesFragment() {

    override fun getMoviesQuery(database: FirebaseRealtimeDatabase): Query {
        return database.favoriteMovies
    }

    override fun getTitle(): String = "Favorite Movies"
}
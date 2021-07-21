package com.example.myfavoritemovie.ui.movies.added

import com.google.firebase.database.Query
import com.example.myfavoritemovie.data.source.firebase.FirebaseRealtimeDatabase

class FavoriteMoviesFragment : AddedMoviesFragment() {

    override fun getMoviesQuery(database: FirebaseRealtimeDatabase): Query {
        return database.favoriteMovies
    }

    override fun getTitle(): String = "Favorite Movies"
}
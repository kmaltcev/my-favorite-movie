package com.example.myfavoritemovie.app.dependency

import com.example.myfavoritemovie.domain.repository.SearchRepository
import com.example.myfavoritemovie.data.source.tmdb.createTMDbApiService
import com.example.myfavoritemovie.domain.actions.search.*

class SearchModule(private val moviesModule: MoviesModule) {
    val searchMoviesAction by lazy {
        SearchMoviesAction(searchRepository)
    }

    val searchPostersByMovieAction by lazy {
        SearchPostersByMovieAction(searchRepository)
    }

    val searchNamesForMovieAction by lazy {
        SearchNamesForMovieAction(searchRepository)
    }

    val searchUpcomingAction by lazy {
        SearchUpcomingAction(searchRepository)
    }

    private val searchRepository by lazy {
        SearchRepository(
            createTMDbApiService(),
            moviesModule.firebaseRealtimeDatabase
        )
    }
}
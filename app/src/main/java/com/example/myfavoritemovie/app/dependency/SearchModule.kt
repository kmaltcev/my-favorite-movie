package com.example.myfavoritemovie.app.dependency

import com.example.myfavoritemovie.data.source.tmdb.createTMDbApiService
import com.example.myfavoritemovie.domain.actions.search.SearchMoviesAction
import com.example.myfavoritemovie.domain.actions.search.SearchNamesForMovieAction
import com.example.myfavoritemovie.domain.actions.search.SearchPostersByMovieAction
import com.example.myfavoritemovie.domain.actions.search.SearchUpcomingAction
import com.example.myfavoritemovie.domain.repository.SearchRepository

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
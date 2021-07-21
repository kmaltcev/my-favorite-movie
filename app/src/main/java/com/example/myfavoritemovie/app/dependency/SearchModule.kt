package com.example.myfavoritemovie.app.dependency

import com.example.myfavoritemovie.data.repository.SearchRepositoryImpl
import com.example.myfavoritemovie.data.source.tmdb.createTMDbApiService
import com.example.myfavoritemovie.domain.actions.search.SearchByMovieAction
import com.example.myfavoritemovie.domain.actions.search.SearchMoviesAction
import com.example.myfavoritemovie.domain.actions.search.SearchNamesForMovieAction
import com.example.myfavoritemovie.domain.actions.search.SearchPostersByMovieAction

class SearchModule(private val moviesModule: MoviesModule) {
    val searchByMovieAction by lazy {
        SearchByMovieAction(
            searchMoviesAction
        )
    }
    val searchMoviesAction by lazy {
        SearchMoviesAction(
            searchRepository
        )
    }

    val searchPostersByMovieAction by lazy {
        SearchPostersByMovieAction(
            searchRepository
        )
    }

    val searchNamesForMovieAction by lazy {
        SearchNamesForMovieAction(
            searchRepository
        )
    }

    private val searchRepository by lazy {
        SearchRepositoryImpl(
            createTMDbApiService(),
            moviesModule.firebaseRealtimeDatabase
        )
    }
}
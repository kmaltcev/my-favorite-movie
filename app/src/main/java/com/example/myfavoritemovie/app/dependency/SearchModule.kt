package com.example.myfavoritemovie.app.dependency

import com.example.myfavoritemovie.data.repository.SearchRepositoryImpl
import com.example.myfavoritemovie.data.source.tmdb.createTMDbApiService
import com.example.myfavoritemovie.domain.usecase.search.SearchByMovieUseCase
import com.example.myfavoritemovie.domain.usecase.search.SearchMoviesUseCase
import com.example.myfavoritemovie.domain.usecase.search.SearchNamesForMovieUseCase
import com.example.myfavoritemovie.domain.usecase.search.SearchPostersByMovieUseCase

class SearchModule(private val moviesModule: MoviesModule) {
    val searchByMovieUseCase by lazy {
        SearchByMovieUseCase(
            searchMoviesUseCase
        )
    }
    val searchMoviesUseCase by lazy {
        SearchMoviesUseCase(
            searchRepository
        )
    }

    val searchPostersByMovieUseCase by lazy {
        SearchPostersByMovieUseCase(
            searchRepository
        )
    }

    val searchNamesForMovieUseCase by lazy {
        SearchNamesForMovieUseCase(
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
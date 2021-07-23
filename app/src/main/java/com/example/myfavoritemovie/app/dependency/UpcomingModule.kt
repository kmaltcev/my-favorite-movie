package com.example.myfavoritemovie.app.dependency

import com.example.myfavoritemovie.data.source.tmdb.createTMDbApiService
import com.example.myfavoritemovie.domain.actions.search.SearchUpcomingAction
import com.example.myfavoritemovie.domain.repository.SearchRepository

class UpcomingModule(private val moviesModule: MoviesModule) {
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
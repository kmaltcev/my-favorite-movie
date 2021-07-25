package com.example.myfavoritemovie.app.dependency

import com.example.myfavoritemovie.data.source.firebase.FirebaseRealtimeDatabase
import com.example.myfavoritemovie.data.source.firebase.FirebaseStorageDataSource
import com.example.myfavoritemovie.domain.actions.movies.*
import com.example.myfavoritemovie.domain.repository.MovieChangesRepository
import com.example.myfavoritemovie.domain.repository.MoviesRepository

class MoviesModule {
    val addNeedToWatchMovieAction by lazy {
        AddNeedToWatchMovieAction(
            moviesRepository,
            movieChangesRepository,
            prepareMovieToAddAction
        )
    }
    val addFavoriteMovieAction by lazy {
        AddFavoriteMovieAction(
            moviesRepository,
            movieChangesRepository,
            prepareMovieToAddAction
        )
    }
    val deleteMovieAction by lazy {
        DeleteMovieAction(
            moviesRepository,
            movieChangesRepository
        )
    }
    val moveToWatchMovieAction by lazy {
        MoveToFavoriteMoviesAction(
            moviesRepository,
            movieChangesRepository
        )
    }

    private val prepareMovieToAddAction by lazy {
        PrepareMovieToAddAction(prepareSeriesToAddAction)
    }

    private val prepareSeriesToAddAction by lazy { PrepareSeriesToAddAction() }

    val getChangedMovieAction by lazy {
        GetChangedMovieAction(movieChangesRepository)
    }

    private val moviesRepository by lazy {
        MoviesRepository(firebaseRealtimeDatabase, firebaseStorageDataSource)
    }

    private val movieChangesRepository by lazy { MovieChangesRepository() }
    val firebaseRealtimeDatabase: FirebaseRealtimeDatabase by lazy { FirebaseRealtimeDatabase() }
    private val firebaseStorageDataSource by lazy { FirebaseStorageDataSource() }
}

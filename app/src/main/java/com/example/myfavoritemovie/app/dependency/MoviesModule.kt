package com.example.myfavoritemovie.app.dependency

import com.example.myfavoritemovie.data.repository.MovieChangesRepositoryImpl
import com.example.myfavoritemovie.data.repository.MoviesRepositoryImpl
import com.example.myfavoritemovie.data.source.firebase.FirebaseRealtimeDatabase
import com.example.myfavoritemovie.data.source.firebase.FirebaseStorageDataSource
import com.example.myfavoritemovie.domain.actions.movies.*

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
    val updateMovieUseCase by lazy {
        LinkMovieUseCase(
            moviesRepository,
            prepareSeriesToAddAction
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
    val changeMoviePosterAction by lazy {
        ChangeMoviePosterAction(
            moviesRepository
        )
    }

    val changeMovieNameAction by lazy {
        ChangeMovieNameAction(
            moviesRepository
        )
    }

    private val prepareMovieToAddAction by lazy {
        PrepareMovieToAddAction(
            prepareSeriesToAddAction
        )
    }

    private val prepareSeriesToAddAction by lazy { PrepareSeriesToAddUseCase() }

    val getChangedMovieAction by lazy {
        GetChangedMovieAction(
            movieChangesRepository
        )
    }

    private val moviesRepository by lazy {
        MoviesRepositoryImpl(firebaseRealtimeDatabase, firebaseStorageDataSource)
    }

    private val movieChangesRepository by lazy { MovieChangesRepositoryImpl() }
    val firebaseRealtimeDatabase: FirebaseRealtimeDatabase by lazy { FirebaseRealtimeDatabase() }
    private val firebaseStorageDataSource by lazy { FirebaseStorageDataSource() }
}

package com.example.myfavoritemovie.app.dependency

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfavoritemovie.app.app
import com.example.myfavoritemovie.ui.movies.MovieViewModel
import com.example.myfavoritemovie.ui.movies.dialog.MovieDialogViewModel
import com.example.myfavoritemovie.ui.search.SearchViewModel
import com.example.myfavoritemovie.ui.upcoming.UpcomingViewModel

class ViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val moviesModule = context.app.moviesModule
    private val searchModule = context.app.searchModule
    private val upcomingModule = context.app.upcomingModule

    private val movieViewModel: MovieViewModel
        get() = MovieViewModel()

    private val searchViewModel: SearchViewModel
        get() = SearchViewModel(
            searchModule.searchMoviesAction,
            moviesModule.getChangedMovieAction
        )
    private val upcomingViewModel: UpcomingViewModel
        get() = UpcomingViewModel(
            upcomingModule.searchUpcomingAction,
            moviesModule.getChangedMovieAction
        )

    private val movieDialogViewModel: MovieDialogViewModel
        get() = MovieDialogViewModel(
            moviesModule.deleteMovieAction,
            moviesModule.addFavoriteMovieAction,
            moviesModule.addNeedToWatchMovieAction,
            moviesModule.moveToWatchMovieAction
        )

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(SearchViewModel::class.java) -> {
                searchViewModel
            }
            isAssignableFrom(MovieViewModel::class.java) -> {
                movieViewModel
            }
            isAssignableFrom(MovieDialogViewModel::class.java) -> {
                movieDialogViewModel
            }
            isAssignableFrom(UpcomingViewModel::class.java) -> {
                upcomingViewModel
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}
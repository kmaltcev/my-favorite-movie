package com.example.myfavoritemovie.app.dependency

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfavoritemovie.app.app
import com.example.myfavoritemovie.ui.linkmovie.LinkMovieViewModel
import com.example.myfavoritemovie.ui.movies.MovieViewModel
import com.example.myfavoritemovie.ui.movies.dialog.MovieDialogViewModel
import com.example.myfavoritemovie.ui.search.SearchViewModel

class ViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val moviesModule = context.app.moviesModule
    private val searchModule = context.app.searchModule

    private val movieViewModel: MovieViewModel
        get() = MovieViewModel(
            moviesModule.addGoodMovieUseCase,
            moviesModule.addNeedToWatchMovieUseCase
        )

    private val searchViewModel: SearchViewModel
        get() = SearchViewModel(
            searchModule.searchMoviesUseCase,
            moviesModule.getChangedMovieUseCase
        )

    private val linkMovieViewModel: LinkMovieViewModel
        get() = LinkMovieViewModel(
            searchModule.searchByMovieUseCase,
            moviesModule.updateMovieUseCase,
            moviesModule.getChangedMovieUseCase
        )

    private val movieDialogViewModel: MovieDialogViewModel
        get() = MovieDialogViewModel(
            moviesModule.deleteMovieUseCase,
            moviesModule.moveToWatchMovieUseCase,
            moviesModule.changeMoviePosterUseCase,
            searchModule.searchPostersByMovieUseCase,
            searchModule.searchNamesForMovieUseCase,
            moviesModule.changeMovieNameUseCase
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
            isAssignableFrom(LinkMovieViewModel::class.java) -> {
                linkMovieViewModel
            }
            isAssignableFrom(MovieDialogViewModel::class.java) -> {
                movieDialogViewModel
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T

}
package com.example.myfavoritemovie.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfavoritemovie.domain.actions.movies.AddFavoriteMovieAction
import com.example.myfavoritemovie.domain.actions.movies.AddNeedToWatchMovieAction
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.ui.Event
import kotlinx.coroutines.launch

class MovieViewModel(
    private val addFavoriteMovieAction: AddFavoriteMovieAction,
    private val addNeedToWatchMovieAction: AddNeedToWatchMovieAction
) : ViewModel() {

    private val _openMovieDialog = MutableLiveData<Event<Movie>>()
    val openMovieDialog: LiveData<Event<Movie>> = _openMovieDialog

    fun addFavoriteMovie(movie: Movie) = viewModelScope.launch {
        addFavoriteMovieAction(movie)
    }

    fun addNeedToWatchMovie(movie: Movie) = viewModelScope.launch {
        addNeedToWatchMovieAction(movie)
    }

    fun openMovieDialog(movie: Movie) {
        _openMovieDialog.value = Event(movie)
    }
}